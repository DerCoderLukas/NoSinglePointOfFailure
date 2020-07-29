# NoSinglePointOfFailure

The simple illustration of a no single point of failure system. It consists of two basic types, the databases and the nodes. As soon as a node wants to write a new value in its database, the database searches for the master database around the network and gives it the value. This then notifies all other databases in the network and allows the value to be synchronized to everyone.

# Requirements

• Java 14

# Usage

A precise application is not yet certain at this point. However, the application could be used as a framework for such a system or for another case as an example for implementation. Certainly there are other methods that do not allow a single point of failure, but it is very simple for database synchronization and for this case.

# Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.
