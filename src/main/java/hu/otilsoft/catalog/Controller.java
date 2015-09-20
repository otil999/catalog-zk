package hu.otilsoft.catalog;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabbox;

public class Controller extends SelectorComposer<Component> {
	private static final long serialVersionUID = -5005389448762933836L;
	
	@Listen("onClick = menuitem")
	public void menuAction(MouseEvent mouseEvent) {
		Component x = mouseEvent.getTarget();
		System.out.println("XXX:"+x.getId());
		Component c = Path.getComponent("/window/tabbox");
		c.setVisible(true);

		Tab tab = new Tab("Content type");
		tab.setClosable(true);
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.setParent(((Tabbox) c).getTabpanels());
		((Tabbox) c).getTabs().insertBefore(tab, null);
	}
}
