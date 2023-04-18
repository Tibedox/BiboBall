package ru.tibedox.biboball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

import jdk.nashorn.internal.parser.JSONParser;

public class DynamicPoly {
    Body body;
    float x, y;
    float width, height;
    float angle;

    Vector2[] chain;

    public DynamicPoly(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef(); // создаём объект настроек тела
        bodyDef.type = BodyDef.BodyType.DynamicBody; // определяем тип
        bodyDef.position.set(x, y); // устанавливаем стартовую позицию

        body = world.createBody(bodyDef); // создаём само тело

        fromJson();
        ChainShape shape = new ChainShape(); // создаём объект формы
        shape.createLoop(chain);
        //PolygonShape shape = new PolygonShape(); // создаём объект формы
        //shape.set(chain);

        FixtureDef fixtureDef = new FixtureDef(); // Создаём настройки fixture для тела
        fixtureDef.shape = shape; // форма
        fixtureDef.density = 0.5f; // плотность
        fixtureDef.friction = 0.4f; // трение
        fixtureDef.restitution = 0.8f; // упругость /Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef); // создаём fixture

        shape.dispose(); // форму необходимо удалять
        body.setAngularVelocity(1);
    }

    void fromJson() {
        // read from json
        FileHandle file = Gdx.files.internal("starpoly.json");
        String jsonString = file.readString();
        Json json = new Json();
        Vertices verts;
        verts = json.fromJson(Vertices.class, jsonString);

        System.out.println(verts.toString());
        chain = new Vector2[verts.vertices.length];
        for (int i = 0; i < chain.length; i++) {
            chain[i] = new Vector2((verts.vertices[i].x-100)/100, (verts.vertices[i].y-100)/100);
            System.out.print(chain[i].x+" "+ chain[i].y+" - ");
        }

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

class Vertice{
    float x, y;
}
class Vertices{
    Vertice[] vertices;
}