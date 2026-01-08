import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.projectFeatures.hashiCorpVaultConnection
import jetbrains.buildServer.configs.kotlin.remoteParameters.hashiCorpVaultParameter

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2025.11"

project {
// my comment
    buildType(Build)

    params {
        select("sel1", "sel1",
                options = listOf("sel2", "sel3", "sel4"))
        hashiCorpVaultParameter {
            name = "par2_vault"
            query = "secrets/data/teamcity-qa/tc-qa-test-infrastructure!/tcSpaceServiceAccSshPubKey"
            vaultId = "vaultchubatova"
        }
    }

    features {
        hashiCorpVaultConnection {
            id = "vaultchubatova"
            name = "HashiCorp Vault"
            url = "https://vault.intellij.net"
            authMethod = appRole {
                roleId = "secrets-teamcity-qa"
                secretId = "credentialsJSON:f433e72c-7a67-4e0a-b5f9-12208893b8bb"
            }
        }
    }
}

object Build : BuildType({
    name = "Build"

    params {
        select("env.reqular", "regular2",
                options = listOf("a", "b", "c"))
        hashiCorpVaultParameter {
            name = "par1_vault"
            query = "secrets/data/teamcity-qa/tc-qa-test-infrastructure!/tcSpaceServiceAccSshPubKey"
            vaultId = "vaultchubatova"
        }
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    features {
        perfmon {
        }
    }
})
