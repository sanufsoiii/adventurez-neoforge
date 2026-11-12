# AdventureZ: Unofficial NeoForge Port

Unofficial port of **[AdventureZ](https://github.com/Globox1997/AdventureZ)** by **Globox_Z** (Fabric 1.21.1, version 1.5.2) to **NeoForge 1.21.1**.

## Status: early alpha

The port builds and the client starts to the main menu without errors. **Gameplay has not been tested in a world yet**: expect bugs, and please report them (see below). Dedicated server has not been tested either.

## Requirements

- Minecraft 1.21.1, NeoForge 21.1.200 or newer
- [Cloth Config](https://modrinth.com/mod/cloth-config) for NeoForge

Integrations with mods that have no NeoForge 1.21.1 version (VoidZ and others) are inactive.

## Getting textures, models and sounds

All AdventureZ assets (textures, models, sounds, translations) are **All Rights Reserved** by Globox_Z. They are **not included** in this port.

1. Download the original **AdventureZ 1.5.2** for **Fabric 1.21.1** yourself: [CurseForge](https://www.curseforge.com/minecraft/mc-mods/adventurez/files) or [Modrinth](https://modrinth.com/mod/adventurez/version/1.5.2+1.21.1).
2. Put the downloaded `.jar` into your `resourcepacks` folder. **Not** into `mods`.

On startup the port finds the jar and loads **only its client assets** (textures, models, sounds, translations) as a built-in resource pack. Nothing is downloaded by the mod itself. Without the jar the game works, but items and blocks are purple-black, and a one-time notice on the title screen explains what to do.

## Building

Requires JDK 21.

```bash
./gradlew build
```

The mod jar is in `build/libs/`.

## Reporting bugs

This is an **unofficial** port. Please **do not** report its bugs to Globox_Z or on the original issue tracker. Use this repository instead: https://github.com/sanufsoiii/adventurez-neoforge/issues

## License and credits

- AdventureZ was created by **Globox_Z**: https://github.com/Globox1997/AdventureZ. All gameplay design, art, sounds and names belong to them.
- This port is not affiliated with or endorsed by the original author.
- Code: **GPL-3.0**, same as the original code (see `LICENSE`).
- Folder `_excluded/` keeps original Fabric-only code (data generators, integrations with mods that have no NeoForge 1.21.1 version) for reference; it is not compiled.
