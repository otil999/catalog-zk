package hu.otilsoft.catalog;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.*;

public class Controller extends SelectorComposer<Component> {
	private static final long serialVersionUID = -5005389448762933836L;
	
	@Listen("onClick = menuitem")
	public void menuAction(MouseEvent mouseEvent) {
		Component x = mouseEvent.getTarget();
		String menuItemId = x.getId();
		switch (menuItemId) {
			case "media":
				Component c = Path.getComponent("/window/tabbox");
				c.setVisible(true);

				Tab tab = new Tab("Content type");
				tab.setClosable(true);

				Tabpanel tabpanel = new Tabpanel();
				tabpanel.setParent(((Tabbox) c).getTabpanels());
				((Tabbox) c).getTabs().insertBefore(tab, null);

				Listbox listbox = new Listbox();
				listbox.setParent(tabpanel);
				listbox.setModel(new SimpleListModel<String>(new String[] { "kutya", "cica"}));
//				System.out.println("XXXX:"+listbox.getHeads());
//				System.out.println("XXXX:"+listbox.getListhead());
//				listbox.getListhead().appendChild(new Listheader("CUCC"));
//				System.out.println("XXXX:"+listbox.getHeads());
//				System.out.println("XXXX:"+listbox.getListhead());
				break;
		}
	}
}
