# lightChat - Simple, convenient and functional minecraft chat.
# Functional:
**[`[1]`](https://github.com/rTiRe/lightChat/blob/main/README.md#globalchat-localchat-worldchat)** Global Chat  
**[`[2]`](https://github.com/rTiRe/lightChat/blob/main/README.md#globalchat-localchat-worldchat)** Local Chat  
**[`[3]`](https://github.com/rTiRe/lightChat/blob/main/README.md#globalchat-localchat-worldchat)** World Chat  
**[`[4]`](https://github.com/rTiRe/lightChat/blob/main/README.md#private)** Private Chat  
**[`[5]`](https://github.com/rTiRe/lightChat/blob/main/README.md#ping)** Player Mentions  
**[`[6]`](https://github.com/rTiRe/lightChat/blob/main/README.md#messages)** Custom join and quit messages

**`Global Chat`** - Messages are visible to all players on the server.  
**`Local Chat`** - Messages are visible to all players within a specified number of blocks from the message sender.  
**`World Chat`** - Messages are visible to players who are in the same dimension as the message sender.  
**`Private Chat`** - Personal correspondence of two players.  
**`Player Mentions`** - Highlighting the nickname of the mentioned player in the chat (only for him // for everyone), accompanied by a sound notification and text above the hot bar for the mentioned player.  
**`Custom ... messages`** - Customizable player join and quit messages.  
# Requirements
+ **`SpigotMC 1.18.1`** - Kernel.  
+ **`Vault`** (optional) - Plugin.
# Placeholders and Text formatting
*Some of the lines have comments (`# comment`) above them indicating supported placeholders or other notes and possible values.*  
|        Placeholder       |Description                                                                                  |
|:------------------------:|---------------------------------------------------------------------------------------------|
| **`{message}`**          | Message sent by the player or config message.                                               |
| **`{sender}`**           | Message sender nickname.                                                                    |
| **`{senderPrefix}`**     | Message sender prefix. <br>**:bangbang: Requires `Vault`**.                                 |
| **`{senderSuffix}`**     | Message sender suffix. <br>**:bangbang: Requires `Vault`**.                                 |
| **`{recipient}`**        | Message recipient nickname.                                                                 |
| **`{recipientPrefix}`**  | Message recipient prefix. <br>**:bangbang: Requires `Vault`**.                              |
| **`{recipientSuffix}`**  | Message recipient suffix. <br>**:bangbang: Requires `Vault`**.                              |
| **`{mentioned}`**        | Nickname of the mentioned player.                                                           |
| **`{mentionedPrefix}`**  | Prefix of the mentioned player. <br>**:bangbang: Requires `Vault`**.                        |
| **`{mentionedSuffix}`**  | Suffix of the mentioned player. <br>**:bangbang: Requires `Vault`**.                        |
| **`{player}`**           | The nickname of the player the event is associated with.                                    |
| **`{playerPrefix}`**     | The prefix of the player the event is associated with. <br>**:bangbang: Requires `Vault`**. |
| **`{playerSuffix}`**     | The suffix of the player the event is associated with. <br>**:bangbang: Requires `Vault`**. |
| **`{globalChatPrefix}`** | Global chat anchor symbol.                                                                  |
| **`{localChatPrefix}`**  | Local chat anchor symbol.                                                                   |
| **`{worldChatPrefix}`**  | World chat anchor symbol.                                                                   |
  
***Hereinafter, all placeholders will be framed like this, however, you can change this in the config.***


*They will also be described [below](https://github.com/rTiRe/lightChat/blob/main/README.md#config-setup).*  
<br>
By default in the config **`&`** (*You can change this sign in the config*) is replaced by **`§`**. Text can be [formatted](https://github.com/rTiRe/lightChat/blob/main/README.md#useful-links) in the same way as regular text in minecraft.

# Config setup
## Сonfig

|        String        |Description                                                     |Placeholders                                                                              |
|:--------------------:|----------------------------------------------------------------|------------------------------------------------------------------------------------------|
| **`placeholder`**    | Specifies how the placeholder should look like.                | <ul><li>**`placeholder`**</li></ul>  DO NOT USE `{placeholder}` HERE. `placeholder` ONLY!|
| **`formatSymbol`**   | Specifies how the formatting mark should look like. Default `&`|                                                                                          |


## Chats.globalChat, Chats.localChat, Chats.worldChat:

|        String        |Description                                                                                                                                                                                                     |Placeholders                                                                                                                                                                                                               |
|:--------------------:|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`enable`**         | Sets the chat mode. `true` - enabled, `any other` - disabled.                                                                                                                                                  |                                                                                                                                                                                                                           |
| **`prefix`**         | Anchor symbol that determines the chat in which the player writes.                                                                                                                                             |                                                                                                                                                                                                                           |
| **`format`**         | General view of the message with placeholders.                                                                                                                                                                 | <ul><li>**`{sender}`**</li> <li>**`{senderPrefix}`**</li> <li>**`{senderSuffix}`**</li> <li>**`{message}`**</li></ul>                                                                                                     |
| **`messageColor`**   | Message color.                                                                                                                                                                                                 |                                                                                                                                                                                                                           |
| **`ping`**           | Sets whether player mentions are enabled in this chat. `true` - enabled, `any other` - disabled.                                                                                                               |                                                                                                                                                                                                                           |
| **`chatDistantion`** | The radius of the circle centered at the position of the player who sent the message, inside which the players will see the message sent to them. <br>**Only for `localChat`.**                                |                                                                                                                                                                                                                           |
| **`prefixError`**    | Error text with placeholders that the player will see if they enter a non-existent prefix.                                                                                                                     | <ul><li>**`{sender}`**</li> <li>**`{senderPrefix}`**</li> <li>**`{senderSuffix}`**</li> <li>**`{message}`**</li> <li>**`{globalChatPrefix}`**</li> <li>**`{localChatPrefix}`**</li> <li>**`{worldChatPrefix}`**</li></ul> |
| **`noPerms`**        | The message that the sender of the message will see if they do not have [the right to write to this chat](https://github.com/rTiRe/lightChat/blob/main/README.md#permissions). <br>**Leave blank to disable.** | <ul><li>**`{sender}`**</li></ul>                                                                                                                                                                                          |
| **`noPermsPing`**    | The message that the sender of the message with mention will see if they do not have [permission to mention players](https://github.com/rTiRe/lightChat/blob/main/README.md#permissions). <br>**Leave blank to disable.**   | <ul><li>**`{sender}`**</li></ul>                                                                                                                                                                                          |


## Ping:

|       String       |Description                                                                                                                                                                                                                                                                                                                                           |Placeholders                                                                                           |
|:------------------:|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| **`inFormat`**     | Mention input format. Mention will be determined by it.                                                                                                                                                                                                                                                                                              | **Must contain `{mentioned}` placeholder.**                                                           |
| **`outFormat`**    | The output format of the mention with placeholders that will be displayed in the message instead of the input format.                                                                                                                                                                                                                                | <ul><li>**`{mentioned}`**</li> <li>**`{mentionedPrefix}`**</li> <li>**`{mentionedSuffix}`**</li></ul> |
| **`marker`**       | A special mention format for the mentioned player. <br>**Leave blank to disable.**                                                                                                                                                                                                                                                                   | <ul><li>**`{mentioned}`**</li> <li>**`{mentionedPrefix}`**</li> <li>**`{mentionedSuffix}`**</li></ul> |
| **`message`**      | The message that the mentioned player will see above the hotbar if he is mentioned. <br>**Leave blank to disable.**                                                                                                                                                                                                                                  |                                                                                                       |
| **`myself`**       | The message the player will see if they try to mention themselves. <br>**Leave blank to disable.**                                                                                                                                                                                                                                                   |                                                                                                       |
| **`sound.sound`**  | The [sound](https://github.com/rTiRe/lightChat/blob/main/README.md#useful-links) the player will hear if mentioned. <br>**Leave blank to turn off audio playback.**                                                                                                                                                                                  |                                                                                                       |
| **`sound.volume`** | The volume of the corresponding sound. The maximum value is `1`, but more is possible. It will be heard the same at the playback point, but the more, the better it will be heard at a greater distance from the playback point. For example: `1` - weakly audible at a distance of 15 blocks, `10` - well audible even at a distance of 150 blocks. |                                                                                                       |
| **`sound.pitch`**  | Audio playback speed. `1` - normal. **Less** is slower, **more** is faster.                                                                                                                                                                                                                                                                          |                                                                                                       |


## Private

|           String           |Description                                                                                                                                                                                                                                                                                                                               |Placeholders                                                                                                                                             |
|:--------------------------:|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`commands`**             | Commands for sending private messages. List them with commas. <br>**Leave blank to disable private chats.**                                                                                                                                                                                                                              |                                                                                                                                                         |
| **`senderFormat`**         | The format of the message that the sender sees.                                                                                                                                                                                                                                                                                          | <ul><li> **`{sender}`**</li> <li>**`{recipient}`**</li> <li>**`{recipientPrefix}`**</li> <li>**`{recipientSuffix}`**</li> <li>**`{message}`**</li></ul> |
| **`recipientFormat`**      | The format of the message that the recipient sees.                                                                                                                                                                                                                                                                                       | <ul><li>**`{recipient}`**</li> <li>**`{sender}`**</li> <li>**`{senderPrefix}`**</li> <li>**`{senderSuffix}`**</li> <li>**`{message}`**</li></ul>        |
| **`message`**              | The message that the recipient of the message will see above the hotbar. <br>**Leave blank to disable.**                                                                                                                                                                                                                                 | <ul><li>**`{sender}`**</li> <li>**`{senderPrefix}`**</li> <li>**`{senderSuffix}`**</li></ul>                                                            |
| **`myself`**               | The message that the player will see if they try to send a message to themselves. <br>**Leave blank to disable.**                                                                                                                                                                                                                        | <ul><li>**`{message}`**</li></ul>                                                                                                                       |
| **`error`**                | The message that the player will see if they fail to send a message to the specified player.                                                                                                                                                                                                                                             | <ul><li>**`{recipient}`**</li></ul>                                                                                                                     |
| **`noPerms`**              | The message that the player will see if they do not have the [right to send private messages](https://github.com/rTiRe/lightChat/blob/main/README.md#permissions).                                                                                                                                                                       |                                                                                                                                                         |
| **`sound.recipientSound`** | The [sound](https://github.com/rTiRe/lightChat/blob/main/README.md#useful-links) that the recipient of the message will hear. <br>**Leave blank to disable.**                                                                                                                                                                            |                                                                                                                                                         |
| **`sound.senderSound`**    | The [sound](https://github.com/rTiRe/lightChat/blob/main/README.md#useful-links) that the sender of the message will hear. <br>**Leave blank to disable.**                                                                                                                                                                               |                                                                                                                                                         |
| **`sound.volume`**         | The volume of both sounds. The maximum value is `1`, but more is possible. It will be heard the same at the playback point, but the more, the better it will be heard at a greater distance from the playback point. For example: `1` - weakly audible at a distance of 15 blocks, `10` - well audible even at a distance of 150 blocks. |                                                                                                                                                         |
| **`sound.pitch`**          | The playback speed of both sounds. `1` - normal. **Less** is slower, **more** is faster.                                                                                                                                                                                                                                                 |                                                                                                                                                         |


## Messages
:arrow_up_down: - Items marked with this sign can have multiple lines. Each must be written on a new line, starting with "-".  
For example:  
```
 - 'message1'
 - 'message2'
 - 'message3'
 - 'message4'
 - 'message5'
```
Of all the entries, a random one will be chosen.
  
|        String       |Description                                                                                                                                                                                                       |Placeholders                                                                                 |
|:-------------------:|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|
| **`join.format`**   | The format of the join message. <br>**Leave blank to disable.**                                                                                                                                                  | <ul><li>**`{message}`** - Server login message.</li></ul>                                   |
| **`join.first`**    | Message when a player first joins the server. :arrow_up_down:                                                                                                                                                    | <ul><li>**`{player}`**</li> <li>**`{playerPrefix}`**</li> <li>**`{playerSuffix}`**</li></ul>|
| **`join.main`**     | Message when a player joins the server. :arrow_up_down:                                                                                                                                                          | <ul><li>**`{player}`**</li> <li>**`{playerPrefix}`**</li> <li>**`{playerSuffix}`**</li></ul>|
| **`join.nickname`** | Where `nickname` is the nickname of the entered player. One of these messages will be displayed when a player with the nickname `nickname` logs in. There can be any number of such structures. :arrow_up_down:  | <ul><li>**`{player}`**</li> <li>**`{playerPrefix}`**</li> <li>**`{playerSuffix}`**</li></ul>|
| **`quit.format`**   | The format of the quit message. <br>**Leave blank to disable.**                                                                                                                                                  | <ul><li>**`{message}`** - Server logout message.</li></ul>                                  |
| **`quit.main`**     | Message when a player quits the server. :arrow_up_down:                                                                                                                                                          | <ul><li>**`{player}`**</li> <li>**`{playerPrefix}`**</li> <li>**`{playerSuffix}`**</li></ul>|
| **`quit.nickname`** | Where `nickname` is the nickname of the entered player. One of these messages will be displayed when a player with the nickname `nickname` logs out. There can be any number of such structures. :arrow_up_down: | <ul><li>**`{player}`**</li> <li>**`{playerPrefix}`**</li> <li>**`{playerSuffix}`**</li></ul>|

## Reload
|     String    |Description                                                                                                                                                                                                   |Placeholders                      |
|:-------------:|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------|
| **`success`** | The message that the sender of the command will see when the config is successfully reloaded. <br>**Leave blank to disable.**                                                                                | <ul><li>**`{sender}`**</li></ul> |
| **`noPerms`** | The message that the sender of the command will see if they do not have [permission to use the command](https://github.com/rTiRe/lightChat/blob/main/README.md#permissions). <br>**Leave blank to disable.** | <ul><li>**`{sender}`**</li></ul> |
| **`error`**   | The message that the sender of the command will see if an error occurs while reloading the config. <br>**Leave blank to disable.**                                                                           |                                  |

# Preset Commands
|        Command        |Aliases                 |Description                                                                                      |
|:---------------------:|------------------------|-------------------------------------------------------------------------------------------------|
| `/lightchat <reload>` | `lc`, `lightc`, `lchat`| Plugin configuration command. <br><br>Arguments: <ul><li>**`reload`** - config reload</li></ul> |


# Permissions
+ **`lc.chat.<Chat>.write`** - Allows owner of the right to send `<Chat>` messages. 
+ **`lc.chat.<Chat>.see`** - Allows owner of the right to receive `<Chat>` messages.
+ **`lc.chat.<Chat>.mention`** - Allows owner of the right to mention people in the `<Chat>`. The right does not apply to private chat.
+ **`lc.reload`** - Gives the right to reload the config.

# Useful links
+ **[Names of all sounds in the game](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html).**  
+ **[Text formatting in Minecraft](https://minecraft.fandom.com/wiki/Formatting_codes).**
