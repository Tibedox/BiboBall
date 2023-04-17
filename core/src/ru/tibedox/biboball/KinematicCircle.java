package ru.tibedox.biboball;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class KinematicCircle {
    Body body;
    float r = 6;
    float width, height;
    boolean isJump;
    float jumpHigh;

    KinematicCircle(World world, float x, float y, float r){
        BodyDef bodyDef = new BodyDef(); // создаём объект настроек тела
        bodyDef.type = BodyDef.BodyType.KinematicBody; // определяем тип
        bodyDef.position.set(x, y); // устанавливаем стартовую позицию

        body = world.createBody(bodyDef); // создаём само тело

        CircleShape circle = new CircleShape(); // создаём объект формы
        circle.setRadius(r);

        FixtureDef fixtureDef = new FixtureDef(); // Создаём настройки fixture для тела
        fixtureDef.shape = circle; // форма
        fixtureDef.density = 5f; // плотность
        fixtureDef.friction = 5.4f; // трение
        fixtureDef.restitution = 0.0f; // упругость /Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef); // создаём fixture

        circle.dispose(); // форму необходимо удалять

        width = height = r*2;
    }

    float getX(){
        return body.getPosition().x;
    }

    float getY(){
        return body.getPosition().y;
    }

    void setY(float x){

    }

    float getR(){
        return r;
    }

    void move(){

    }
}
