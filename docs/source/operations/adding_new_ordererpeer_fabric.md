<a name = "adding-new-orderer-to-existing-organization-in-a-running-fabric-network"></a>
# Adding a new RAFT orderer to existing Orderer organization in Hyperledger Fabric

  - [Prerequisites](#prerequisites)
  - [Modifying Configuration File](#modifying-configuration-file)
  - [Run playbook](#run-playbook)
  - [Chaincode Installation](#chaincode-installation)


<a name = "prerequisites"></a>
## Prerequisites
To add a new Orderer node, a fully configured Fabric network must be present already, i.e. a Fabric network which has Orderers, Peers, Channels (with all Peers already in the channels) and the organization to which the peer is being added. The corresponding crypto materials should also be present in their respective Hashicorp Vault. 

---
**NOTE**: Addition of a new Orderer node has been tested on an existing network which is created by BAF. Networks created using other methods may be suitable but this has not been tested by BAF team.
This works only for RAFT Orderer.

---

<a name = "modifying-configuration-file"></a>
## Modifying Configuration File

A Sample configuration file for adding new orderer is available [here](https://github.com/hyperledger-labs/blockchain-automation-framework/blob/master/platforms/hyperledger-fabric/configuration/samples/network-fabricv2-raft-add-orderer.yaml). Please go through this file and all the comments there and edit accordingly.

For generic instructions on the Fabric configuration file, refer [this guide](./fabric_networkyaml.md).

While modifying the configuration file(`network.yaml`) for adding new peer, all the existing orderers should have `status` tag as `existing` and the new orderers should have `status` tag as `new` under `network.organizations` as

    network:
      organizations:
        - organization:
          org_status: existing  # org_status must be existing when adding peer
          ..
          ..
          services:
            orderers:
            - orderer:
              ..
              ..
              status: new   # new for new peers(s)              
            - orderer:
              ..
              ..
              status: existing   # existing for existing peers(s)
            

The `network.yaml` file should contain the specific `network.organization` details.

Ensure the following is considered when adding the new orderer on a different cluster:
- The CA server is accessible publicly or at least from the new cluster.
- The CA server public certificate is stored in a local path and that path provided in network.yaml.
- There is a single Hashicorp Vault and both clusters (as well as ansible controller) can access it.
- Admin User certs have been already generated and store in Vault (this is taken care of by deploy-network.yaml playbook if you are using BAF to setup the network).
- The `network.env.type` is different for different clusters.
- The GitOps release directory `gitops.release_dir` is different for different clusters.

<a name = "run-playbook"></a>
## Run playbook

The [add-orderer.yaml](https://github.com/hyperledger-labs/blockchain-automation-framework/tree/master/platforms/hyperledger-fabric/configuration/add-orderer.yaml) playbook is used to add a new peer to an existing organization in the existing network. This can be done using the following command

```
ansible-playbook platforms/hyperledger-fabric/configuration/add-orderer.yaml --extra-vars "@path-to-network.yaml"
```

---
**NOTE:** The `orderer.status` is not required when the network is deployed for the first time but is mandatory for addition of new orderer.
