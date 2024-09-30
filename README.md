# Bot For Private Campaigns. (JDA)
Bot For Private Campaigns is a GitHub where you can download a base to create your own Discord bot in JDA dedicated to role-playing campaigns.

With this bot you can create, in its version 0.1, a private channel with the name you want along with a role with the name you want. This serves to automate the creation of private channels for campaigns.
These channels will allow you to host the users to whom you assign the role you have created.

# How do I use this bot?

To use the bot, you must first install a Java IDE. We recommend using [Intellij IDEA](https://www.jetbrains.com/idea/) for its ease of understanding.

Then you must download the project, either from a Git program such as GitHub Desktop or by .ZIP.

Next, you must compile the bot by going to:
1. File
2. Project Structure > Artifacts
3. Add > JAR > From modules with dependencies

Then go to Build > Build Artifacts > NameOfYourJAR

Now what you must do is fill in the fields that appear in the .env file.

# How do I create the App?

You have to go to the Discord Developer Portal and click on "New Application", then go to "Bot" once you have entered your application, then check all the "Privileged Gateway Intents" boxes within "Bot" and that's it.

## Create your own game.
```
/crearcanal nombrecanal: Example nombrerol: Example
```

With this command you can first create the "Channel" and then the "Role" you want. The channel will become private.
