package de.dercoder.nspof;

import com.google.inject.Guice;
import com.google.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class NSPOFNodeTest {
  @Inject private NSPOFDatabaseFactory<String> databaseFactory;
  @Inject private NSPOFNodeFactory<String> nodeFactory;
  @Inject private NSPOFDatabaseRegistry databaseRegistry;
  @Inject private NSPOFNodeRegistry nodeRegistry;

  @BeforeEach
  void initialize() {
    var nspofModule = NSPOFModule.<String>create();
    var injector = Guice.createInjector(nspofModule);
    injector.injectMembers(this);
    createDatabases();
  }

  void createDatabases() {
    int databaseNumber = 2;
    for ( int i = 0; i < databaseNumber; i++ ) {
      databaseFactory.createDatabase();
    }
  }

  @Test
  void testNodeCreation() {
    var node = nodeFactory.createNode();
    assertTrue(nodeRegistry.contains(node));
  }

  @Test
  void testNodeDatabaseHandling() {
    var firstNode = nodeFactory.createNode();
    var secondNode = nodeFactory.createNode();
    firstNode.writeDatabaseEntry("TestEntry");
    assertEquals(1, secondNode.readDatabaseEntries().size());
  }

  @Test
  void testNodeDatabaseCrashHandling() {
    var firstNode = nodeFactory.createNode();
    firstNode.writeDatabaseEntry("TestEntry");
    databaseRegistry.findMaster().ifPresent(masterDatabase -> {
      ((NSPOFDatabase) masterDatabase).close();
    });
    var firstDatabaseEntry = firstNode.readDatabaseEntries().get(0);
    assertEquals("TestEntry", firstDatabaseEntry);
  }

  @Test
  void testNodeClosing() {
    var node = nodeFactory.createNode();
    node.close();
    assertEquals(0, nodeRegistry.findAll().size());
  }
}
