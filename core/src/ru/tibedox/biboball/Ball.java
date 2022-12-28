package ru.tibedox.biboball;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {
    Body body;
    float x, y;
    float width, height;
    float angle;
    float r;

    public Ball(World world, float x, float y, float r) {
        BodyDef bodyDef = new BodyDef(); // создаём объект настроек тела
        bodyDef.type = BodyDef.BodyType.DynamicBody; // определяем тип
        bodyDef.position.set(x, y); // устанавливаем стартовую позицию

        body = world.createBody(bodyDef); // создаём само тело

        CircleShape circle = new CircleShape(); // создаём объект формы
        circle.setRadius(r);

        FixtureDef fixtureDef = new FixtureDef(); // Создаём настройки fixture для тела
        fixtureDef.shape = circle; // форма
        fixtureDef.density = 0.5f; // плотность
        fixtureDef.friction = 0.4f; // трение
        fixtureDef.restitution = 0.8f; // упругость /Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef); // создаём fixture

        circle.dispose(); // форму необходимо удалять

        this.r = r;
        width = height = r*2;
    }

    void update(){
        x = body.getPosition().x-width/2;
        y = body.getPosition().y-height/2;
        angle = MathUtils.radiansToDegrees*body.getAngle();
    }

    public float getImgX() {
        return body.getPosition().x-width/2;
    }

    public float getImgY() {
        return body.getPosition().y-width/2;
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public float getAngle() {
        return MathUtils.radiansToDegrees*body.getAngle();
    }
}
