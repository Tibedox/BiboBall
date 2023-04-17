package ru.tibedox.biboball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class StaticBox {
    Body body;
    StaticBox(World world, float x, float y, float width, float height){
        BodyDef bodyDef = new BodyDef(); // создаём объект настроек тела
        bodyDef.type = BodyDef.BodyType.StaticBody; // определяем тип
        bodyDef.position.set(new Vector2(x, y)); // устанавливаем стартовую позицию

        body = world.createBody(bodyDef); // создаём само тело

        PolygonShape box = new PolygonShape(); // создаём полигон формы
        box.setAsBox(width, height); // ширина и высота

        body.createFixture(box, 0.0f); // создаём fixture из полигона формы и лепим на тело

        box.dispose(); // форму необходимо удалять
    }
}
