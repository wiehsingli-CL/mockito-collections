package uk.co.webamoeba.mockito.collections.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/**
 * @author James Kennard
 * @param <T>
 */
public class HashOrderedSetBackedSortedSet<T> implements SortedSet<T> {

	private OrderedSet<T> orderedSet = new HashOrderedSet<T>();

	public int size() {
		return orderedSet.size();
	}

	public boolean isEmpty() {
		return orderedSet.isEmpty();
	}

	public boolean contains(Object o) {
		return orderedSet.contains(o);
	}

	public Iterator<T> iterator() {
		return orderedSet.iterator();
	}

	public Object[] toArray() {
		return orderedSet.toArray();
	}

	public <E> E[] toArray(E[] a) {
		return orderedSet.toArray(a);
	}

	public boolean add(T e) {
		return orderedSet.add(e);
	}

	public boolean remove(Object o) {
		return orderedSet.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return orderedSet.containsAll(c);
	}

	public boolean addAll(Collection<? extends T> c) {
		return orderedSet.addAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return orderedSet.retainAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return orderedSet.removeAll(c);
	}

	public void clear() {
		orderedSet.clear();
	}

	@Override
	public int hashCode() {
		int result = 31 + ((orderedSet == null) ? 0 : orderedSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		HashOrderedSetBackedSortedSet other = (HashOrderedSetBackedSortedSet) obj;
		if (orderedSet == null && other.orderedSet != null) {
			return false;
		} else if (orderedSet != null && !orderedSet.equals(other.orderedSet)) {
			return false;
		}
		return true;
	}

	public Comparator<? super T> comparator() {
		throw new UnsupportedOperationException("Mockito-Collections injected SortedSet does not support this method");
	}

	public SortedSet<T> subSet(T fromElement, T toElement) {
		throw new UnsupportedOperationException("Mockito-Collections injected SortedSet does not support this method");
	}

	public SortedSet<T> headSet(T toElement) {
		throw new UnsupportedOperationException("Mockito-Collections injected SortedSet does not support this method");
	}

	public SortedSet<T> tailSet(T fromElement) {
		throw new UnsupportedOperationException("Mockito-Collections injected SortedSet does not support this method");
	}

	public T first() {
		return orderedSet.iterator().next();
	}

	public T last() {
		// This isn't very efficient, but given the context in which this will be used it is unimportant
		T last = null;
		for (T candidate : orderedSet) {
			last = candidate;
		}
		return last;
	}

}