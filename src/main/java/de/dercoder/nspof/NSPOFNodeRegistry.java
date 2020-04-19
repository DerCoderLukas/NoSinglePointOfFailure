package de.dercoder.nspof;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public final class NSPOFNodeRegistry<T> {
    private final Set<NSPOFNode<T>> nspofNodes;

    private NSPOFNodeRegistry(Set<NSPOFNode<T>> nspofNodes) {
        this.nspofNodes = nspofNodes;
    }

    public void register(NSPOFNode<T> nspofNode) {
        Preconditions.checkNotNull(nspofNode);
        nspofNodes.add(nspofNode);
    }

    public void unregister(NSPOFNode<T> nspofNode) {
        Preconditions.checkNotNull(nspofNode);
        nspofNodes.remove(nspofNode);
    }

    public boolean contains(NSPOFNode nspofNode) {
        Preconditions.checkNotNull(nspofNode);
        return nspofNodes.contains(nspofNode);
    }

    public List<NSPOFNode<T>> findAll() {
        return List.copyOf(nspofNodes);
    }

    public static <T> NSPOFNodeRegistry<T> empty() {
        return new NSPOFNodeRegistry<T>(Sets.newHashSet());
    }
}
