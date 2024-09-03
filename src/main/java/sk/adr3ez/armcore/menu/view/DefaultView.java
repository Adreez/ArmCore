package sk.adr3ez.armcore.menu.view;

import sk.adr3ez.armcore.menu.util.Range;

/**
 * Default view for {@link sk.adr3ez.armcore.menu.Menu} which is used when no {@link WindowView} is specified or when some slots are not used in any {@link WindowView}.
 */
public final class DefaultView extends WindowView {
	
	public DefaultView(Integer... slots) {
		super(slots);
	}
	
	public DefaultView(Range range) {
		super(range);
	}
}
