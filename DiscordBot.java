package budgettracker;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {

    BudgetManager manager = new BudgetManager();

    public void startBot() {
        try {
            JDABuilder.createDefault(System.getenv("DISCORD_BOT_TOKEN"))
                    .addEventListeners(this)
                    .build();
            System.out.println("Bot is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        TextChannel ch = event.getChannel().asTextChannel();
        String msg = event.getMessage().getContentRaw();

       
        if (msg.startsWith("!addincome")) {
            try {
                double amt = Double.parseDouble(msg.split(" ")[1]);
                manager.addIncome(amt);
                ch.sendMessage("Income added: ₹" + amt).queue();
            } catch (Exception e) {
                ch.sendMessage("Use: !addincome <amount>").queue();
            }
        }

        else if (msg.startsWith("!addexpense")) {
            try {
                String[] parts = msg.split(" ");
                String name = parts[1];
                double amt = Double.parseDouble(parts[2]);
                manager.addExpense(name, amt);
                ch.sendMessage("Expense added!").queue();
            } catch (Exception e) {
                ch.sendMessage("Use: !addexpense <name> <amount>").queue();
            }
        }

        else if (msg.equals("!showexpenses")) {
            ch.sendMessage(manager.showExpenses()).queue();
        }

        else if (msg.equals("!budget")) {
            ch.sendMessage(manager.calculateBudget()).queue();
        }

        
        else if (msg.equals("!tip")) {
            ch.sendMessage(manager.savingTip()).queue();
        }
    }
}
