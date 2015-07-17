package hu.otilsoft.catalog;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Window;

/**
 *
 * @author otil
 */
public class MyComposer extends Window implements Composer {
	private static final long serialVersionUID = 4974530531483201149L;

	public void doAfterCompose(Component cmpnt) throws Exception {
        ((Window) cmpnt).setTitle("OtilSoft Catalog with ZK");
    }
	
	public void onCreate() {
		System.out.println("onCreate has been called.");
	}

    public void onOk(Event event) {
    	System.out.println("OK pressed. " + event);
        setTitle("Ok pressed.");
    }

    public void onCancel() {
        setTitle("Cancel pressed.");
    }
    
    public void onLater() {
    	System.out.println("On Later invoked.");
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	Clients.showBusy(null, false);
    }
    
    public void onLongRun(Event event) {
		Clients.showBusy("Execute...", true);
		Events.echoEvent("onLater", getParent(), null);    
    }
}
