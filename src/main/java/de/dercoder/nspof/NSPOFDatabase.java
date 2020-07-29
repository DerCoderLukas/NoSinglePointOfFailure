package de.dercoder.nspof;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

public final class NSPOFDatabase<T> {
  private final NSPOFDatabaseRegistry<T> databaseRegistry;
  private final List<T> data;
  private NSPOFDatabaseRecognition databaseRecognition;

  private NSPOFDatabase(
    NSPOFDatabaseRegistry<T> databaseRegistry, List<T> data
  ) {
    this.databaseRegistry = databaseRegistry;
    this.data = data;
    this.databaseRecognition = NSPOFDatabaseRecognition.SLAVE;
  }

  public void add(T value) {
    Preconditions.checkNotNull(value);
    data.add(value);
    if (isMaster()) {
      distributeDatabaseAddition(value);
    }
  }

  private void distributeDatabaseAddition(T value) {
    databaseRegistry.findSlaves().stream().forEach(nspofDatabase -> {
      nspofDatabase.add(value);
    });
  }

  public void remove(T value) {
    Preconditions.checkNotNull(value);
    data.remove(value);
    if (isMaster()) {
      distributeDatabaseRemoval(value);
    }
  }

  private void distributeDatabaseRemoval(T value) {
    databaseRegistry.findSlaves().stream().forEach(nspofDatabase -> {
      nspofDatabase.remove(value);
    });
  }

  public void close() {
    databaseRegistry.unregister(this);
  }

  public List<T> data() {
    return List.copyOf(data);
  }

  public boolean isMaster() {
    return databaseRecognition == NSPOFDatabaseRecognition.MASTER;
  }

  public boolean isSlave() {
    return databaseRecognition == NSPOFDatabaseRecognition.SLAVE;
  }

  public void setDatabaseRecognition(NSPOFDatabaseRecognition databaseRecognition) {
    Preconditions.checkNotNull(databaseRecognition);
    this.databaseRecognition = databaseRecognition;
  }

  public static <T> NSPOFDatabase<T> empty(
    NSPOFDatabaseRegistry<T> databaseRegistry
  ) {
    Preconditions.checkNotNull(databaseRegistry);
    return new NSPOFDatabase<T>(databaseRegistry, Lists.newArrayList());
  }
}
