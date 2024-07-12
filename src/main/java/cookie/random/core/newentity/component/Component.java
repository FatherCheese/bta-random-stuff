package cookie.random.core.newentity.component;

import cookie.random.core.newentity.NewMob;

public abstract class Component {

	public NewMob baseObject = null;

	public void onInit() {

	}

	public abstract void tick();
}
