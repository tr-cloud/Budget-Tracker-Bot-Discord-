package budgettracker;

import java.util.Arrays;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class Main extends ListenerAdapter {

    private final BudgetManager manager = new BudgetManager();

    public static void main(String[] args) {
        String token = System.getenv("DISCORD_BOT_TOKEN");
        if (token == null) {
            System.out.println("ERROR: Discord bot token is missing!");
            return;
        }

        try {
            JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .setActivity(Activity.playing("Budget Tracking"))
                    .addEventListeners(new Main())
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
        String[] parts = msg.split(" ");

        // ✨ NEW COMMAND: Button Menu
        if (msg.equalsIgnoreCase("!menu")) {
            ch.sendMessage("Choose an option:")
                    .addActionRow(
                            Button.primary("add_income", "Add Income"),
                            Button.primary("add_expense", "Add Expense")
                    )
                    .addActionRow(
                            Button.success("show_budget", "Show Budget"),
                            Button.secondary("show_expenses", "Show Expenses")
                    )
                    .queue();
            return;
        }

        
        if (msg.startsWith("!addincome")) {
            try {
                double amt = Double.parseDouble(parts[1]);
                manager.addIncome(amt);
                ch.sendMessage("Income added: ₹" + amt).queue();
            } catch (Exception e) {
                ch.sendMessage("Use: !addincome <amount>").queue();
            }
        }

       
        else if (msg.startsWith("!addexpense")) {
            try {
                String name = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
                double amt = Double.parseDouble(parts[parts.length - 1]);
                manager.addExpense(name, amt);
                ch.sendMessage("Expense added: " + name + " ₹" + amt).queue();
            } catch (Exception e) {
                ch.sendMessage("Use: !addexpense <name> <amount>").queue();
            }
        }

       
        else if (msg.equalsIgnoreCase("!showexpenses")) {
            ch.sendMessage(manager.showExpenses()).queue();
        }

       
        else if (msg.equalsIgnoreCase("!budget")) {
            ch.sendMessage(manager.calculateBudget()).queue();
        }

        
        else if (msg.equalsIgnoreCase("!tip")) {
            ch.sendMessage(manager.savingTip()).queue();
        }
    }

   
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String id = event.getComponentId();

        switch (id) {

            case "add_income":
                event.reply("Use: **!addincome <amount>**").queue();
                break;

            case "add_expense":
                event.reply("Use: **!addexpense <name> <amount>**").queue();
                break;

            case "show_budget":
                event.reply(manager.calculateBudget()).queue();
                break;

            case "show_expenses":
                event.reply(manager.showExpenses()).queue();
                break;

            default:
                event.reply("Unknown button!").setEphemeral(true).queue();
        }
    }
}
