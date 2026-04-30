---
name: aircraftwar-release-packaging
description: Use when packaging or releasing the AircraftWar Java Swing project as a runnable JAR or GitHub release asset
---

# AircraftWar Release Packaging

## Quick Reference

- Build from the project root or release worktree root.
- Compile production classes into `build/classes`.
- Copy `src/images` to `build/classes/images` and `src/videos` to `build/classes/videos` before creating the JAR.
- Create the JAR with main class `edu.hitsz.application.Main`.
- For Windows exe releases, use `jpackage --type app-image` and zip the generated `AircraftWar` directory.
- Verify with JUnit and a JAR resource smoke check before tagging.

## Commands

```powershell
Remove-Item -Recurse -Force build\classes,build\release -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force build\classes,build\release | Out-Null
javac -encoding UTF-8 -d build\classes (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName)
Copy-Item -Recurse src\images build\classes\images
Copy-Item -Recurse src\videos build\classes\videos
jar --create --file build\release\AircraftWar-0.1.0.jar --main-class edu.hitsz.application.Main -C build\classes .
```

```powershell
jpackage --type app-image --name AircraftWar --app-version 0.1.1 --input build\release\package-input --main-jar AircraftWar-0.1.1.jar --dest build\release\windows-x64
Compress-Archive -Path build\release\windows-x64\AircraftWar -DestinationPath build\release\AircraftWar-0.1.1-windows-x64.zip -Force
```

## Checks

- `jar --list --file build\release\AircraftWar-0.1.0.jar` should include `edu/hitsz/application/Main.class`, `images/hero.png`, and `videos/bgm.wav`.
- Use JShell with the release JAR as the only classpath and confirm `ImageManager.HERO_IMAGE` is non-null.
- The Windows ZIP should contain `AircraftWar/AircraftWar.exe`, `AircraftWar/app/AircraftWar-0.1.1.jar`, and `AircraftWar/runtime/bin/java.dll`.