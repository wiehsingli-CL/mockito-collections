import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class InjectionDetails {

	private Set<Object> injectees;

	private Set<Object> injectables;

	/**
	 * @param injectees {@link Object}s into which we want to inject {@link Collection}s of injectables.
	 * @param injectables {@link Object}s that can be injected into {@link Collection}s in the injectees.
	 */
	public InjectionDetails(Set<Object> injectees, Set<Object> injectables) {
		if (injectees == null) {
			throw new IllegalArgumentException("Injectees must not be null");
		}
		this.injectees = injectees;
		if (injectables == null) {
			this.injectables = Collections.emptySet();
		} else {
			this.injectables = injectables;
		}
	}
	/**
	 * @return The {@link Object}s into which we want to inject {@link Collection}s. If there are
	 * 		   no injectables this method will return an empty {@link Set}, this method will
	 * 		   never return <code>null</code>.
	 */
	public Set<Object> getInjectees() {
		return injectees;
	}

	/**
	 * @return {@link Set} of {@link Object}s that can be injected into {@link Collection}s.
	 * 		   If there are no injectables, this method will return an empty {@link Set}, this
	 * 		   method will never return <code>null</code>.
	 */
	public Set<Object> getInjectables() {
		return injectables;
	}
}