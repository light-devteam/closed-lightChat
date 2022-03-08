# lightChat
Simple, convenient and functional minecraft chat.
## Functional:
**`[1]`** Global Chat  
**`[2]`** Local Chat  
**`[3]`** World Chat  
**`[4]`** Private Chat  
**`[5]`** Player Mentions  
**`[6]`** Custom join and quit messages

**`Global Chat`** - Messages are visible to all players on the server.  
**`Local Chat`** - Messages are visible to all players within a specified number of blocks from the message sender.  
**`World Chat`** - Messages are visible to players who are in the same dimension as the message sender.  
**`Private Chat`** - Personal correspondence of two players.  
**`Player Mentions`** - Highlighting the nickname of the mentioned player in the chat (only for him // for everyone), accompanied by a sound notification and text above the hot bar for the mentioned player.  
**`Custom ... messages`** - Customizable player join and quit messages.  

## Placeholders
*Some of the lines have comments (`# comment`) above them indicating supported placeholders or other notes and possible values.*  
*They will also be described [below](https://github.com/rTiRe/lightChat/blob/main/README.md#config-setup).*

## Config setup
### globalChat, localChat, worldChat:
+ **`enable`** - Sets the chat mode. `true` - enabled, `any other` - disabled.  
+ **`prefix`** - Anchor symbol that determines the chat in which the player writes.  
+ **`format`** - General view of the message with placeholders. **Placeholders**:
> **`{senderPrefix}`** - Message sender prefix. Requires `Vault`.  
> **`{sender}`** - Message sender nickname.  
> **`{senderSuffix}`** - Message sender suffix. Requires `Vault`.  
> **`{message}`** - Message sent by the player.  

+ **`messageColor`** - Message color.  
+ **`ping`** - Sets whether player mentions are enabled in this chat. `true` - enabled, `any other` - disabled.  
+ **`chatDistantion`** - The radius of the circle centered at the position of the player who sent the message, inside which the players will see the message sent to them. **Only for `localChat`.**  
+ **`prefixError`** - Error text with placeholders that the player will see if they enter a non-existent prefix. **Placeholders:**  
> **`{senderPrefix}`** - Message sender prefix. Requires `Vault`.  
> **`{sender}`** - Message sender nickname.  
> **`{senderSuffix}`** - Message sender suffix. Requires `Vault`.  
> **`{message}`** - Message sent by the player.  
> **`{globalChatPrefix}`** - Global chat anchor symbol.  
> **`{localChatPrefix}`** - Local chat anchor symbol.  
> **`{worldChatPrefix}`** - World chat anchor symbol.  

### Ping:
+ **`inFormat`** - Mention input format. Mention will be determined by it. **Must contain `{mentioned}` placeholder.**  
+ **`outFormat`** - The output format of the mention with placeholders that will be displayed in the message instead of the input format. **Placeholders:**  
> **`{mentionedPrefix}`** - Prefix of the mentioned player. Requires `Vault`.  
> **`{mentioned}`** - Nickname of the mentioned player.  
> **`{mentionedSuffix}`** - Suffix of the mentioned player. Requires `Vault`.  

+ **`marker`** - A special mention format for the mentioned player. **Leave blank to disable. Placeholders:**  
> **`{mentionedPrefix}`** - Prefix of the mentioned player. Requires `Vault`.  
> **`{mentioned}`** - Nickname of the mentioned player.  
> **`{mentionedSuffix}`** - Suffix of the mentioned player. Requires `Vault`.  

+ **`message`** - The message that the mentioned player will see above the hotbar if he is mentioned. **Leave blank to disable.**  
+ **`myself`** - The message the player will see if they try to mention themselves if **`marker.visibility`** is equal to `mentioned`. **Leave blank to disable.**  
+ **`sound.sound`** - The [sound](https://github.com/rTiRe/lightChat/blob/main/README.md#useful-links) the player will hear if mentioned. **Leave blank to turn off audio playback.**  
+ **`sound.volume`** - The volume of the corresponding sound. The maximum value is `1`, but more is possible. It will be heard the same at the playback point, but the more, the better it will be heard at a greater distance from the playback point. For example: `1` - weakly audible at a distance of 15 blocks, `10` - well audible even at a distance of 150 blocks.  
+ **`sound.pitch`** - Audio playback speed. `1` - normal. **Less** is slower, **more** is faster.  

### Private
+ **`commands`** - Commands for sending private messages. List them with commas. **Leave blank to disable private chats.**  
+ **`senderFormat`** - The format of the message that the sender sees. **Placeholders:**  
> **`{sender}`** - Message sender nickname.  
> **`{recipientPrefix}`** - Message recipient prefix. Requires `Vault`.  
> **`{recipient}`** - Message recipient nickname.  
> **`{recipientSuffix}`** - Message recipient suffix. Requires `Vault`.  
> **`{message}`** - Message sent by the player.  

+ **`recipientFormat`** - The format of the message that the recipient sees. **Placeholders:**  
> **`{recipient}`** - Message recipient nickname.  
> **`{senderPrefix}`** - Message sender prefix. Requires `Vault`.  
> **`{sender}`** - Message sender nickname.  
> **`{senderSuffix}`** - Message sender suffix. Requires `Vault`.  
> **`{message}`** - Message sent by the player.  

+ **`message`** - The message that the recipient of the message will see above the hotbar. **Leave blank to disable. Placeholders:**
> **`{senderPrefix}`** - Message sender prefix. Requires `Vault`.  
> **`{sender}`** - Message sender nickname.  
> **`{senderSuffix}`** - Message sender suffix. Requires `Vault`. 

+ **`myself`** - The message that the player will see if they try to send a message to themselves. **Leave blank to disable. Placeholders:**
> **`{message}`** - Message sent by the player.  

+ **`error`** - The message that the player will see if they fail to send a message to the specified player. **Placeholders:**  
> **`{recipient}`** - Message recipient nickname.  

+ **`noPerms`** - The message that the player will see if they do not have the [right to send private messages](https://github.com/rTiRe/lightChat/blob/main/README.md#permissions).  
+ **`sound.recipientSound`** - The [sound](https://github.com/rTiRe/lightChat/blob/main/README.md#useful-links) that the recipient of the message will hear. **Leave blank to disable.**  
+ **`sound.senderSound`** - The [sound](https://github.com/rTiRe/lightChat/blob/main/README.md#useful-links) that the sender of the message will hear. **Leave blank to disable.**
+ **`sound.volume`** - The volume of both sounds. The maximum value is `1`, but more is possible. It will be heard the same at the playback point, but the more, the better it will be heard at a greater distance from the playback point. For example: `1` - weakly audible at a distance of 15 blocks, `10` - well audible even at a distance of 150 blocks.  
+ **`sound.pitch`** - The playback speed of both sounds. `1` - normal. **Less** is slower, **more** is faster.  

## Permissions
+ **`lc.private`** - Gives the owner the right to send private messages.

## Useful links
+ **[Names of all sounds in the game](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html).**
