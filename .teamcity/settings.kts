import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.projectFeatures.hashiCorpVaultConnection
import jetbrains.buildServer.configs.kotlin.remoteParameters.hashiCorpVaultParameter



version = "2025.11"

project {

    buildType(Build)

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
//comment chubatova
object Build : BuildType({
    name = "Build"

    params {
        hashiCorpVaultParameter {
            name = "par1_vault"
            query = "secrets/data/teamcity-qa/tc-qa-test-infrastructure!/tcSpaceServiceAccSshPubKey"
            vaultId = "vaultchubatova"
            param("buildTypeId", "Project1_Build")
        }
         select("env.reqular", "regular1",
                    options = listOf("a", "b", "c"))
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    features {
        perfmon {
        }
    }
})
