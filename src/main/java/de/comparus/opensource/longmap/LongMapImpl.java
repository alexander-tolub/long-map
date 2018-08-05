package de.comparus.opensource.longmap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LongMapImpl<V> implements LongMap<V> {

    private final int DEFAULT_NUMBER_OF_BUCKETS = 16;

    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    private float loadFactor;

    private int numberOfBuckets;

    private ArrayList<HashNode<V>> bucketArray;
    private Set<Long> keySet;

    public LongMapImpl() {
        bucketArray = new ArrayList<>(DEFAULT_NUMBER_OF_BUCKETS);
        keySet = new HashSet<>(DEFAULT_NUMBER_OF_BUCKETS);
        loadFactor = DEFAULT_LOAD_FACTOR;
        numberOfBuckets = DEFAULT_NUMBER_OF_BUCKETS;

        for (int i = 0; i < DEFAULT_NUMBER_OF_BUCKETS; i++) {
            bucketArray.add(null);
        }
    }

    public V put(long key, V value) {
        int bucketIndex = getBucketIndex(key);
        if (keySet.contains(key)) {
            HashNode<V> existingValue = bucketArray.get(bucketIndex);

            while (existingValue != null) {
                if (existingValue.getKey() == key) {
                    break;
                }
                existingValue = existingValue.getNext();
            }
            existingValue.setValue(value);
        } else {
            HashNode<V> oldHead = bucketArray.get(bucketIndex);
            HashNode<V> newHead = new HashNode<V>(key, value)
                    .setNext(oldHead);
            bucketArray.set(bucketIndex, newHead);
            keySet.add(key);
            if (size() / numberOfBuckets >= loadFactor) {
                ArrayList<HashNode<V>> temp = bucketArray;
                bucketArray = new ArrayList<>();
                numberOfBuckets = numberOfBuckets * 2;

                for (int i = 0; i < numberOfBuckets; i++) {
                    bucketArray.add(null);
                }

                for (HashNode<V> hashNode : temp) {
                    while (hashNode != null) {
                        put(hashNode.getKey(), hashNode.getValue());
                        hashNode = hashNode.next;
                    }
                }
            }
        }
        return value;
    }

    public V get(long key) {
        if (keySet.contains(key)) {
            int bucketIndex = getBucketIndex(key);
            HashNode<V> existingValue = bucketArray.get(bucketIndex);

            while (existingValue != null) {
                if (existingValue.getKey() == key) {
                    return existingValue.value;
                }
                existingValue = existingValue.getNext();
            }
        }
        return null;
    }

    public V remove(long key) {
        if (keySet.contains(key)) {
            int bucketIndex = getBucketIndex(key);
            HashNode<V> head = bucketArray.get(bucketIndex);
            HashNode<V> previous = null;
            while (head != null) {
                if (head.getKey() == key) {
                    break;
                }

                previous = head;
                head = head.getNext();
            }

            if (previous != null) {
                previous.setNext(head.getNext());
            } else {
                bucketArray.set(bucketIndex, head.getNext());
            }
            keySet.remove(key);
            return head.getValue();
        }
        return null;
    }

    public boolean isEmpty() {
        return !(size() > 0);
    }

    public boolean containsKey(long key) {
        return keySet.contains(key);
    }

    public boolean containsValue(V value) {
        for (HashNode<V> head : bucketArray) {
            while (head != null) {
                if (head.getValue().equals(value)) {
                    return true;
                }
                head = head.getNext();
            }
        }
        return false;
    }

    public long[] keys() {
        long[] result = keySet.stream().mapToLong(l -> l).toArray();
        return result;
    }

    public List<V> values() {
        ArrayList<V> result = new ArrayList<>();
        bucketArray.stream().map(n -> {
            ArrayList<V> nodes = new ArrayList<>();
            while (n != null) {
                nodes.add(n.getValue());
                n = n.getNext();
            }
            return nodes;
        }).forEach(result::addAll);
        return result;
    }

    public long size() {
        return keySet.size();
    }

    public void clear() {
        for (int i = 0; i < bucketArray.size(); i++) {
            bucketArray.set(i, null);
        }
        keySet.clear();
    }

    private int getBucketIndex(long key) {
        long hash = 17;
        hash = 31 * hash + key;
        long index = hash % numberOfBuckets;
        return (int) index;
    }

    private class HashNode<V> {
        long key;
        V value;

        HashNode<V> next;

        public HashNode(long key, V value) {
            this.key = key;
            this.value = value;
        }

        public long getKey() {
            return key;
        }

        public HashNode<V> setKey(long key) {
            this.key = key;
            return this;
        }

        public V getValue() {
            return value;
        }

        public HashNode<V> setValue(V value) {
            this.value = value;
            return this;
        }

        public HashNode<V> getNext() {
            return next;
        }

        public HashNode<V> setNext(HashNode<V> next) {
            this.next = next;
            return this;
        }
    }
}
