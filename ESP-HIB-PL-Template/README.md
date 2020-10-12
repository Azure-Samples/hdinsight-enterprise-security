# Azure HDInsight enterprise security template

Resource Manager templates for creating Azure HDInsight clusters with ESP and HIB and Private Link configuration.

## Features

This template provides the configuration for the following features:

* Enterprise Security Package (ESP)
* HDInsight Identity Broker (HIB)
* Outbound Resource Provider Connection (Removing public IPs)
* Private Link

## Getting Started

### Prerequisites

- AAD-DS
- VNET/Subnet
- ADLS Gen2
- SQL Server
- OUtbound NAT provided through Azure Firewall or other alternatives


## Resources

Related tutorials:

* [Encryption in transit](https://docs.microsoft.com/azure/hdinsight/encryption-in-transit)
* [Transport Layer Security](https://docs.microsoft.com/en-us/azure/hdinsight/transport-layer-security)
