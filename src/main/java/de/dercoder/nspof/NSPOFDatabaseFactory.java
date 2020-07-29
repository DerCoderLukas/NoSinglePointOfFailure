package de.dercoder.nspof;

import com.google.inject.Inject;

import javax.inject.Singleton;

@Singleton
public final class NSPOFDatabaseFactory<T> {
  private final NSPOFDatabaseRegistry databaseRegistry;

  @Inject
  private NSPOFDatabaseFactory(
    NSPOFDatabaseRegistry databaseRegistry
  ) {
    this.databaseRegistry = databaseRegistry;
  }

  public NSPOFDatabase<T> createDatabase() {
    var database = NSPOFDatabase.<T>empty(databaseRegistry);
    databaseRegistry.register(database);
    return database;
  }
}
