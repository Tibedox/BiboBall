package ru.tibedox.biboball;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class DynamicBox {
    Body body;
    float x, y;
    float width, height;
    float angle;

    public DynamicBox(World world, float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef(); // создаём объект настроек тела
        bodyDef.type = BodyDef.BodyType.DynamicBody; // определяем тип
        bodyDef.position.set(x, y); // устанавливаем стартовую позицию

        body = world.createBody(bodyDef); // создаём само тело

        PolygonShape brick = new PolygonShape(); // создаём объект формы
        brick.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef(); // Создаём настройки fixture для тела
        fixtureDef.shape = brick; // форма
        fixtureDef.density = 1.5f; // плотность
        fixtureDef.friction = 0.4f; // трение
        fixtureDef.restitution = 0.6f; // упругость /Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef); // создаём fixture

        brick.dispose(); // форму необходимо удалять

        this.width = width;
        this.height = height;
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
