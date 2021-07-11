<a name = "upgrading-chaincode"></a>
# Upgrading chaincode in Hyperledger Fabric

- [Upgrading chaincode in Hyperledger Fabric](#upgrading-chaincode-in-hyperledger-fabric)
  - [Pre-requisites](#pre-requisites)
  - [Modifying configuration file](#modifying-configuration-file)
  - [Run playbook for Fabric version 1.4.x](#run-playbook-for-fabric-version-14x)
  - [Run playbook for Fabric version 2.2.x](#run-playbook-for-fabric-version-22x)

<a name = "pre_req"></a>
## Pre-requisites
Hyperledger Fabric network deployed, network.yaml configuration file already set and chaincode is installed and instantiated or packaged, approved and commited in case of Fabric version 2.2.

<a name = "create_config_file"></a>
## Modifying configuration file

Refer [this guide](./fabric_networkyaml.md) for details on editing the configuration file.

The `network.yaml` file should contain the specific `network.organizations.services.peers.chaincode.arguments`, `network.organizations.services.peers.chaincode.version` and `network.organizations.services.peers.chaincode.name` variables which are used as arguments while upgrading the chaincode.

For reference, following snippet shows that section of `network.yaml`

```
---
network:
  ..
  ..
  organizations:
    - organization:
      name: manufacturer
      ..
      .. 
      services:
        peers:
        - peer:
          name: peer0          
          ..
          chaincode:
            name: "chaincode_name" #This has to be replaced with the name of the chaincode
            version: "chaincode_version" # This has to be greater than the current version, should be an integer.
            maindirectory: "chaincode_main"  #The main directory where chaincode is needed to be placed
            lang: "java" # The chaincode language, optional field with default vaule of 'go'.
            repository:
              username: "git_username"          # Git Service user who has rights to check-in in all branches
              password: "git_password"
              url: "github.com/hyperledger-labs/blockchain-automation-framework.git"
              branch: develop
              path: "chaincode_src"   #The path to the chaincode 
            arguments: 'chaincode_args' #Arguments to be passed along with the chaincode parameters
            endorsements: "" #Endorsements (if any) provided along with the chaincode
```

<a name = "run_network"></a>
## Run playbook for Fabric version 1.4.x

The playbook [chaincode-upgrade.yaml](https://github.com/hyperledger-labs/blockchain-automation-framework/tree/master/platforms/hyperledger-fabric/configuration/chaincode-upgrade.yaml) is used to upgrade chaincode to a new version in the existing fabric network with version 1.4.x.
This can be done by using the following command

```
    ansible-playbook platforms/hyperledger-fabric/configuration/chaincode-upgrade.yaml --extra-vars "@path-to-network.yaml"
```

## Run playbook for Fabric version 2.2.x

The playbook [chaincode-ops.yaml](https://github.com/hyperledger-labs/blockchain-automation-framework/tree/master/platforms/hyperledger-fabric/configuration/chaincode-ops.yaml) is used to upgrade chaincode to a new version in the existing fabric network with version 2.2.x.
This can be done by using the following command

```
    ansible-playbook platforms/hyperledger-fabric/configuration/chaincode-ops.yaml --extra-vars "@path-to-network.yaml" -e "add_new_org='false'"
```

---
**NOTE:** The Chaincode should be upgraded for all participants of the channel.