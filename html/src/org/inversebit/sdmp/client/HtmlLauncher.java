package org.inversebit.sdmp.client;

import org.inversebit.sdmp.SDMPong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(SDMPong.WIDTH, SDMPong.HEIGHT);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new SDMPong();
        }
}