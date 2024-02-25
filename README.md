# EnvironmentFiles

![Build](https://github.com/azbh111/EnvironmentFiles/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/17044-projectenv.svg)](https://plugins.jetbrains.com/plugin/17044)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/17044.svg)](https://plugins.jetbrains.com/plugin/17044)


<!-- Plugin description -->

EnvironmentFiles plugin provides settings to configure project-wide `.env` files.
Environment variables will be applied to:
* Terminal in all IDEA-based products (tested on macOS)

Run Configuration support:
* Java Run Configurations (IDEA)
* Java Test Run Configurations (IDEA)
* NodeJs Run Configuration (IDEA)

[GitHub](https://github.com/azbh111/EnvironmentFiles)

## Settings
<kbd>EnvironmentFiles</kbd> tool window > <kbd>Add your .env files</kbd>

You can also toggle plugin features: <kbd>EnvironmentFiles</kbd> tool window > <kbd>⚙️</kbd>:
* Enable in Terminal (requires terminal restart)
* Enable in Java Run Configurations
* Enable in Java Test Run Configurations
* Enable in NodeJs Run Configurations
* Inheriting System Environments

## Credits
Source code mostly based on [ProjectEnv](https://github.com/BredoGen/ProjectEnv) plugin by BredoGen. Special thanks for his great work.
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "EnvironmentFiles"</kbd> >
  <kbd>Install Plugin</kbd>

---
**WARNING:** The official does not provide an extension point for injecting environment variables into NodeJsConfiguration. EnvironmentFiles are implemented using reflection. If there are compatibility problems, please raise an issue.

Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
