# HARHARHAR

Five Nights at Deadline places the player in the role of a college engineering student facing the ultimate threat: an unfinished lab report due by morning.

The objective is simple—survive the night and finish your work—but maintaining focus is anything but easy. As time passes, the player’s sanity steadily declines. Lower sanity increases hallucinations, visual distortions, and the appearance of hostile entities that attempt to force the player to fall asleep.

To survive, the player must:

  Manage sanity levels

  Use a flashlight to repel nearby threats

  Doom scroll to recover sanity (at the cost of phone battery)

  Balance limited resources while completing the lab report

If the phone battery runs out or the player collapses from exhaustion, the night ends—and so does the grade.

## Core Mechanics
-Sanity System

Sanity decreases over time and affects visual clarity and enemy behavior.

Flashlight POV
  A restricted field of view simulates limited awareness during exhaustion.

Resource Management
  Phone battery, attention, and time must be carefully balanced.

Psychological Pressure
  The longer the night lasts, the more distorted and hostile the environment becomes.
## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
