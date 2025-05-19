# SpeedHunter
> Can be used to play a popular Minecraft Game Mode Speedrunners VS Hunters — a lightweight, no‑dependencies Minecraft plugin.

## 📦 Features
 - **Automatic Compass Tracking**
  Hunters' compasses always point to nearest Speedrunner
  Updates every second and on player movement
  Cross-world tracking prevention
 - **Lightweight & fast**  
  Minimal performance overhead, compatible with most Spigot/Paper versions. 

## ©️ Commands
 - /sss Secret Command:
  Grants Regeneration 50 (extremely fast healing)
  Hidden particles
  Requires "speedhunter.secret" permission

 - /speedrunner Command:
  Sets player as Speedrunner
  Grants permanent Speed II
  Removes from Hunters list
  Hidden particles

 - /hunter Command:
  Sets player as Hunter
  Grants permanent Strength I
  Gives tracking compass
  Removes from Speedrunners list
  Hidden particles

## 🚀 Installation
 1. Download the latest `SpeedH-1.0.jar` from the [Releases](https://github.com/infenoid/speedhunter-plugin/releases).  
 2. Place the JAR into your server’s `/plugins` folder.  
 3. Restart or reload the server:
    ```shell
    # In‑game or console
    /reload

## 🔒 Permissions
| Permission             | Description                            | Default |
| ---------------------- | -------------------------------------- | :-----: |
| `speedhunter.secret`   | Access to /sss (secret) command        |   `op`  |

## 📄 License
Apache 2.0 License.