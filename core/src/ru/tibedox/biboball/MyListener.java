package ru.tibedox.biboball;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body body;
        if(contact.getFixtureA().getUserData()!= null && contact.getFixtureA().getUserData().equals("z")) {
            if (contact.getFixtureB().getUserData() != null && contact.getFixtureB().getUserData().equals("m")) {
                body = contact.getFixtureB().getBody();
                body.applyLinearImpulse(0, 9, body.getPosition().x, body.getPosition().y, true);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
