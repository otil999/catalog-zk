package hu.otilsoft.catalog;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

/**
 *
 * @author otil
 */
public class MyWindow extends Window {
    /*
    public void doAfterCompose(Component cmpnt) throws Exception {
        ((Window) cmpnt).setTitle("OtilSoft Catalog with ZK");
    }
     * 
     */

    public void onOk() {
        setTitle("Ok pressed.");
    }

    public void onCancel() {
        setTitle("Cancel pressed.");
    }
}
