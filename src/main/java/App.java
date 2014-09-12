
import c8y.IsDevice;
import com.cumulocity.model.authentication.CumulocityCredentials;
import com.cumulocity.model.idtype.GId;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.rest.representation.measurement.MeasurementRepresentation;
import com.cumulocity.sdk.client.Platform;
import com.cumulocity.sdk.client.PlatformImpl;
import com.cumulocity.sdk.client.measurement.MeasurementApi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class App
{
    public static void main( String[] args )
    {
        Platform platform = new PlatformImpl("http://<<url>>", new CumulocityCredentials("<<username>>", "<<pwd>>"));

        ManagedObjectRepresentation piObject = platform.getInventoryApi().get(new GId("44622400"));
        Map<String, Object> objectAttrs = piObject.getAttrs();

        if(!objectAttrs.containsKey("c8y_SupportedMeasurements")) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("c8y_SupportedMeasurements", "test" );

            piObject.setAttrs(map);
            platform.getInventoryApi().update(piObject);
        }

        Map<String, Object> value = new HashMap<String, Object>();
        value.put("Lux", 1255);

        MeasurementRepresentation measurementRepresentation = new MeasurementRepresentation();
        measurementRepresentation.setTime(new Date());
        measurementRepresentation.setType("Lux");
        measurementRepresentation.setSource(piObject);
        measurementRepresentation.setAttrs(value);

        MeasurementApi measurementApi = platform.getMeasurementApi();
        measurementApi.create(measurementRepresentation);


        /*
        InventoryApi inventory = platform.getInventoryApi();
        ManagedObjectRepresentation mo = new ManagedObjectRepresentation();
        mo.setName("Hello, world!");
        mo.set(new IsDevice());
        mo = inventory.create(mo);
        System.out.println("URL: " + mo.getSelf());
        */
    }
}
