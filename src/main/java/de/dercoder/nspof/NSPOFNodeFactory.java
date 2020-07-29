package de.dercoder.nspof;

import com.google.inject.Inject;

public final class NSPOFNodeFactory<T> {
  private final NSPOFNodeRegistry nodeRegistry;
  private final NSPOFDatabaseRegistry databaseRegistry;

  @Inject
  private NSPOFNodeFactory(
    NSPOFNodeRegistry nodeRegistry, NSPOFDatabaseRegistry databaseRegistry
  ) {
    this.nodeRegistry = nodeRegistry;
    this.databaseRegistry = databaseRegistry;
  }

  public NSPOFNode<T> createNode() {
    var node = NSPOFNode.<T>of(nodeRegistry, databaseRegistry);
    nodeRegistry.register(node);
    return node;
  }
}
