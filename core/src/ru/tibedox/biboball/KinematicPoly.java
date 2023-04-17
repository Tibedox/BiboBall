package ru.tibedox.biboball;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class KinematicPoly {
    Body body;
    float[] poly = {100,200, 117,142, 170,171, 142,118, 200,100, 143,83, 170,30, 117,58, 100,0, 82,58, 29,29, 57,83, 0,100, 59,118, 30,170, 83,142, 100,200};

    KinematicPoly(World world, float x, float y, float angle){
        BodyDef bodyDef = new BodyDef(); // создаём объект настроек тела
        bodyDef.type = BodyDef.BodyType.KinematicBody; // определяем тип
        bodyDef.position.set(x, y); // устанавливаем стартовую позицию

        body = world.createBody(bodyDef); // создаём само тело

        for (int i = 0; i < poly.length; i++) {
            poly[i] -= 100;
            poly[i] /= 80;
        }
        ChainShape chain = new ChainShape(); // создаём объект формы
        chain.createChain(poly);

        FixtureDef fixtureDef = new FixtureDef(); // Создаём настройки fixture для тела
        fixtureDef.shape = chain; // форма

        Fixture fixture = body.createFixture(fixtureDef); // создаём fixture

        chain.dispose(); // форму необходимо удалять

        body.setAngularVelocity(angle);
    }

    float getX(){
        return body.getPosition().x;
    }

    float getY(){
        return body.getPosition().y;
    }

    void setY(float x){

    }

    void move(){

    }
}
