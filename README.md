# EnvironmentFiles

![Build](https://github.com/azbh111/EnvironmentFiles/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/17044-projectenv.svg)](https://plugins.jetbrains.com/plugin/23829)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/17044.svg)](https://plugins.jetbrains.com/plugin/23829)


<!-- Plugin description -->

EnvironmentFiles plugin provides settings to configure project-wide `.env` files.
Environment variables will be applied to Terminal and Run Configurations.


[GitHub](https://github.com/azbh111/EnvironmentFiles)

## Features
* Variable Expansion
* Terminal in all IDEA-based products (tested on macOS)
* Java Run Configurations in all IDEA-based products (tested on macOS)
* Java Test Run Configurations in all IDEA-based products (tested on macOS)
* NodeJs Run Configuration in all IDEA-based products (tested on macOS, only works begin Intellij 2022)

## Variable Expansion

The plugin supports referencing environment variables using placeholders and also supports single-line comments.

Placeholder: `${...}`

Single-line comment: `//`, `#`

proirity: `EnvironmentFiles` > `Terminal/RunConfiguration Environment` > `System Environment`


### Examples
Using files 1.env/2.env as examples.

1.env
```shell
# comment will be ignored
ENV_aa=${ENV_aa:aa}\${ENV_aa}
// comment will be ignored
ENV_bb="  ${ENV_aa}bb${ENV_bb\}  "
```

2.env
```shell
ENV_cc='  ${ENV_bb}cc  '
ENV_dd=${ENV_cc}dd
ENV_ee=${ENV_ee:ee}
ENV_ZSH=${ZSH}
```

System Environment
```shell
ZSH=/Users/zyp/.oh-my-zsh
```

NodeJs Run Configuration Environment
```shell
ENV_aa=aaaa
ENV_ff=ff
ENV_ee=eeee
```
![img.png](https://github.com/azbh111/EnvironmentFiles/blob/master/assets/img.png?raw=true)

NodeJs Script
```js
console.log("[" + process.env.ENV_aa + "]")
console.log("[" + process.env.ENV_bb + "]")
console.log("[" + process.env.ENV_cc + "]")
console.log("[" + process.env.ENV_dd + "]")
console.log("[" + process.env.ENV_ee + "]")
console.log("[" + process.env.ENV_ff + "]")
console.log("[" + process.env.ENV_ZSH + "]")
```


Run NodeJs script. This is output text:
```
[aaaa${ENV_aa}]
[  aaaa${ENV_aa}bb${ENV_bb}  ]
[    aaaa${ENV_aa}bb${ENV_bb}  cc  ]
[    aaaa${ENV_aa}bb${ENV_bb}  cc  dd]
[ee]
[ff]
[/Users/zyp/.oh-my-zsh]
```



### Examples

## Settings
<kbd>EnvironmentFiles</kbd> tool window > <kbd>Add your .env files</kbd>

You can also toggle plugin features: <kbd>EnvironmentFiles</kbd> tool window > <kbd>⚙️</kbd>:
* Enable in Terminal (requires terminal restart)
* Enable in Java Run Configurations
* Enable in Java Test Run Configurations
* Enable in NodeJs Run Configurations
* Inheriting System Environments

## Credits
Source code mostly based on [ProjectEnv](https://github.com/BredoGen/ProjectEnv) plugin by Alexander Bayagin. Special thanks for his great work.
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "EnvironmentFiles"</kbd> >
  <kbd>Install Plugin</kbd>

---
**WARNING:** The official does not provide an extension point for injecting environment variables into NodeJsConfiguration. EnvironmentFiles are implemented using reflection. If there are compatibility problems, please raise an issue.

Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
