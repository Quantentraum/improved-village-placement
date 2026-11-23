
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

plugins {
    kotlin("jvm") version "2.1.0"
    id("earth.terrarium.cloche") version "0.10.4"
    id ("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

repositories {
    cloche {
        mavenNeoforgedMeta()
        mavenNeoforged()
        mavenForge()
        mavenFabric()
        mavenParchment()
        librariesMinecraft()
        main()
    }
    mavenCentral()
    maven("https://api.modrinth.com/maven")
}

group = "dev.worldgen.ivp"

val modVersion: String by project
version = modVersion

cloche {
    mappings {
        official()
    }

    metadata {
        modId = "improved_village_placement"
        name = "Improved Village Placement"
        description = "Forces villages to only spawn on flat terrain."
        license = "MIT"
        icon = "pack.png"

        author("Apollo")
    }

    singleTarget {
        fabric {
            loaderVersion = "0.16.13"
            minecraftVersion = "1.20.1"

            dependencies {
                fabricApi("0.92.6")
                modRuntimeOnly("maven.modrinth:lithostitched:1.4.8-fabric-1.20")
                modRuntimeOnly("maven.modrinth:world-preview:QQQzLnFB")
            }

            includedClient()
            runs {
                client()
                server()
            }

            metadata {
                dependencies {
                    dependency {
                        modId = "lithostitched"
                        required = true
                    }
                }
            }
        }
    }
}

publishMods {
    file = file("build/libs/improved-village-placement-$modVersion.jar")
    changelog = """
        This update aims to fix villages being far too rare.
        
        - Loosened the flatness check restrictions slightly.
        - Added small variants of vanilla villages, with smaller flatness checks. These will be most of the villages you see.
        
        These changes make villages only about 50% rarer rather than 90% rarer.
        
        Due to technical issues, this removes the automatic compatibility with most village datapacks/mods. These packs have built-in compatibility:
        - Dungeons and Taverns
        - Terralith
        - Towns and Towers
        - ChoiceTheorem's Overhauled Village
        
        Please see the wiki on the Github page for info on how to add compatibility for other datapacks/mods.
    """.trimIndent()
    type = STABLE
    modLoaders.add("fabric")
    modLoaders.add("forge")
    modLoaders.add("neoforge")
    displayName = "v$modVersion"
    version = modVersion

    dryRun = false

    modrinth {
        accessToken = providers.environmentVariable("TOKEN_MR")
        projectId = "KA0MllEw"
        minecraftVersions.add("1.20.1")
        minecraftVersions.add("1.21.1")
        minecraftVersions.add("1.21.6")
        requires("lithostitched")
    }

    curseforge {
        accessToken = providers.environmentVariable("TOKEN_CF")
        projectId = "1175325"
        minecraftVersions.add("1.20.1")
        minecraftVersions.add("1.21.1")
        minecraftVersions.add("1.21.6")
        requires("lithostitched")
    }
}