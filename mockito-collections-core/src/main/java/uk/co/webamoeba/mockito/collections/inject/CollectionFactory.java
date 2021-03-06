package uk.co.webamoeba.mockito.collections.inject;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentLinkedQueue;

import uk.co.webamoeba.mockito.collections.exception.MockitoCollectionsException;
import uk.co.webamoeba.mockito.collections.util.LinkedSortedSet;
import uk.co.webamoeba.mockito.collections.util.OrderedSet;

/**
 * Factory used to create {@link Collection Collections} of the specified type.
 * 
 * @author James Kennard
 */
public class CollectionFactory {

	/**
	 * Creates a new {@link Collection} of the specified type and populates with the specified contents. If the contents
	 * is <code>null</code> the new {@link Collection} will be empty. The returned {@link Collection} will retain the
	 * order of the elements where ever possible. The order of the elements is guaranteed for all interfaces, this
	 * includes interfaces that would not normally guarantee order in their own right, for example {@link Set}.
	 * 
	 * @param collectionClass
	 *            The type of {@link Collection} to create
	 * @param contents
	 *            The initial contents of the {@link Collection}, this is optional, <code>null</code> is an acceptable
	 *            value
	 * @return A new {@link Collection} of the specified type
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Collection<Object>> T createCollection(Class<T> collectionClass, OrderedSet<?> contents) {
		final T collection;
		if (collectionClass.equals(SortedSet.class)) {
			collection = (T) new LinkedSortedSet();
		} else if (collectionClass.equals(Set.class) || collectionClass.equals(Collection.class)) {
			collection = (T) new OrderedSet(getInitialCapacity(contents));
		} else if (collectionClass.equals(List.class)) {
			collection = (T) new ArrayList(getInitialCapacity(contents));
		} else if (collectionClass.equals(Queue.class)) {
			collection = (T) new ConcurrentLinkedQueue();
		} else if (collectionClass.isInterface()) {
			collection = createCollectionFromUnknownInterface(collectionClass, contents);
		} else if (Modifier.isAbstract(collectionClass.getModifiers())) {
			throw new MockitoCollectionsException("Could not create collection of type " + collectionClass
					+ ", the type is abstract");
		} else {
			throw new MockitoCollectionsException("Could not create collection of type " + collectionClass
					+ ", do not know how to instantiate");
		}
		if (contents != null) {
			collection.addAll(contents);
		}
		return collection;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> T createCollectionFromUnknownInterface(Class<T> collectionClass, Collection<?> contents) {
		final Collection<Object> spiedCollection;
		if (SortedSet.class.isAssignableFrom(collectionClass)) {
			spiedCollection = new LinkedSortedSet();
		} else if (List.class.isAssignableFrom(collectionClass)) {
			spiedCollection = new ArrayList(getInitialCapacity(contents));
		} else if (Queue.class.isAssignableFrom(collectionClass)) {
			spiedCollection = new ConcurrentLinkedQueue();
		} else {
			spiedCollection = new OrderedSet(getInitialCapacity(contents));
		}
		return (T) mock(
				spiedCollection.getClass(),
				withSettings().spiedInstance(spiedCollection).defaultAnswer(CALLS_REAL_METHODS)
						.extraInterfaces(collectionClass));
	}

	/**
	 * Gets the initialCapacity for new {@link Collection Collections} created with the contents from the provided
	 * {@link Collection}. It is anticipated that the {@link Collection} is unlikely to change in size, and thus it
	 * makes sense to use the starting number of elements as the initial capacity.
	 * 
	 * @param contents
	 * @return The initialCapacity for new {@link Collection Collections} created with the contents from the provided
	 *         {@link Collection}
	 */
	private int getInitialCapacity(Collection<?> contents) {
		return contents != null ? contents.size() : 0;
	}
}