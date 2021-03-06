/*
 * OpenRemote, the Home of the Digital Home.
 * Copyright 2008-2015, OpenRemote Inc.
 *
 * See the contributors.txt file in the distribution for a
 * full listing of individual contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.openremote.model.data.json;

import java.util.Enumeration;
import java.util.Map;

import org.openremote.model.DeviceDiscovery;


/**
 * A FlexJSON transformer for {@link DeviceDiscovery} implementation.
 *
 * The expected JSON structure is defined in schema documents located in project's
 * /resources/json directory.
 *
 * @author Juha Lindfors
 */
public class DeviceDiscoveryTransformer extends JSONTransformer<DeviceDiscovery>
{


  // Constants ------------------------------------------------------------------------------------

  /**
   * The JSON property name value used in device discovery JSON document for device identifier: {@value}
   */
  public static  final String DEVICE_IDENTIFIER_JSON_PROPERTY_NAME = "deviceIdentifier";

  /**
   * The JSON property name value used in device discovery JSON document for device names: {@value}
   */
  public static final String DEVICE_NAME_JSON_PROPERTY_NAME = "deviceName";

  /**
   * The JSON property name value used in device discovery JSON document for device protocol:
   * {@value}
   */
  public static final String DEVICE_PROTOCOL_JSON_PROPERTY_NAME = "deviceProtocol";

  /**
   * The JSON property name value used in device discovery JSON document for device model: {@value}
   */
  public static final String DEVICE_MODEL_JSON_PROPERTY_NAME = "deviceModel";

  /**
   * The JSON property name value used in device discovery JSON document for device type: {@value}
   */
  public static final String DEVICE_TYPE_JSON_PROPERTY_NAME = "deviceType";

  /**
   * The JSON property name value used in device discovery JSON document for device attributes:
   * {@value}
   */
  public static final String DEVICE_ATTRIBUTES_JSON_PROPERTY_NAME = "deviceAttributes";



  // Constructors ---------------------------------------------------------------------------------

  /**
   * Constructs a FlexJSON transformer for {@link org.openremote.model.DeviceDiscovery} instances.
   */
  public DeviceDiscoveryTransformer()
  {
    super(DeviceDiscovery.class);
  }


  // JSONTransformer Overrides --------------------------------------------------------------------

  /**
   * Translates a given {@link org.openremote.model.DeviceDiscovery} instance to a JSON data
   * format. The expected JSON properties and object structure is described in the JSON schema
   * documents in project's /resources/json directory.
   *
   * @param discovery
   *            data discovery instance to convert to JSON format
   */
  @Override public void write(DeviceDiscovery discovery)
  {
    DeviceDiscoveryData data = new DeviceDiscoveryData(discovery);

    startObject();

    writeProperty(DEVICE_IDENTIFIER_JSON_PROPERTY_NAME, data.deviceIdentifier);
    writeProperty("deviceName", data.deviceName);
    writeProperty("deviceProtocol", data.deviceProtocol);
    writeProperty("deviceModel", data.model);

    if (!data.type.equals(""))
    {
      writeProperty("deviceType", data.type);
    }

    if (!data.attributes.isEmpty())
    {
      writeProperty("deviceAttributes", data.attributes);
    }

    endObject();
  }


  /**
   * Recreates an {@link org.openremote.model.DeviceDiscovery} instance from a deserialized
   * JSON model prototype.
   *
   * @see   JSONModel
   *
   * @param json
   *          A JSON frame that represents the structures parsed from the JSON document
   *          representing a device discovery instance.
   *
   * @return  A corresponding Java DeviceDiscovery instance
   *
   * @throws  DeserializationException if deserialization fails
   */
  @Override protected DeviceDiscovery deserialize(JSONModel json) throws DeserializationException
  {
    return deserialize(json.getModel());
  }

  /**
   * Recreates an {@link org.openremote.model.DeviceDiscovery} instance from the 'model' object
   * of a deserialized JSON model prototype.
   *
   * @see   ModelObject
   *
   * @param model
   *          A JSON frame that represents the model object parsed from the JSON document
   *          representing a device discovery instance.
   *
   * @return  A corresponding Java DeviceDiscovery instance
   *
   * @throws  DeserializationException if deserialization fails
   */
  protected DeviceDiscovery deserialize(ModelObject model) throws DeserializationException
  {
    try
    {
      String deviceIdentifier = model.getAttribute(DEVICE_IDENTIFIER_JSON_PROPERTY_NAME);
      String deviceName = model.getAttribute(DEVICE_NAME_JSON_PROPERTY_NAME);
      String deviceProtocol = model.getAttribute(DEVICE_PROTOCOL_JSON_PROPERTY_NAME);
      String deviceModel = model.getAttribute(DEVICE_MODEL_JSON_PROPERTY_NAME);
      String deviceType = model.getAttribute(DEVICE_TYPE_JSON_PROPERTY_NAME);

      ModelObject attributes = model.getObject(DEVICE_ATTRIBUTES_JSON_PROPERTY_NAME);

      DeviceDiscovery discovery = new DeviceDiscovery(
          deviceIdentifier, deviceName, deviceProtocol, deviceModel, deviceType);

      if (attributes != null)
      {
        Enumeration<ModelObject.Attribute> enumeration = attributes.getAttributes();

        while (enumeration.hasMoreElements())
        {
          ModelObject.Attribute attr = enumeration.nextElement();

          // TODO : could validate specific attributes here...

          discovery.addAttribute(attr.getName(), attr.getValue());
        }
      }

      return discovery;
    }

    // TODO : convert DeviceDiscovery to throw validation exceptions instead of illegal arg exc.
    // catch (Model.ValidationException exception)

    catch (Throwable throwable)
    {
      throw new DeserializationException(
          "Cannot create new DeviceDiscovery instance, received values are not valid : {0}",
          throwable, throwable.getMessage()
      );
    }
  }


  // Nested Classes -------------------------------------------------------------------------------

  /**
   * This private class makes the data fields in {@link DeviceDiscovery} class visible to
   * this implementation. <p>
   *
   * Note that this class does not make a copy of the mutable device attributes collection.
   * It is up to the enclosing class not to mess things up.
   */
  private static class DeviceDiscoveryData extends DeviceDiscovery
  {
    private String deviceIdentifier = super.deviceIdentifier;
    private String deviceName = super.deviceName;
    private String deviceProtocol = super.protocol;
    private String model = super.model;
    private String type = super.type;
    private Map<String, String> attributes = super.deviceAttributes;

    /**
     * Copy constructor.
     *
     * @param discovery
     *          device discovery data to copy
     */
    private DeviceDiscoveryData(DeviceDiscovery discovery)
    {
      super(discovery);
    }
  }

}

