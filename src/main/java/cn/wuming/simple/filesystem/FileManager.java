package cn.wuming.simple.filesystem;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import cn.wuming.simple.Simple;
import cn.wuming.simple.Wrapper;
import cn.wuming.simple.module.Module;
import cn.wuming.simple.module.ModuleManager;
import cn.wuming.simple.valuesystem.Value;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.*;

/**
 * Project: Simple Client
 * -----------------------------------------------------------
 * Copyright © 2022 | WuMIng | All rights reserved.
 */
@SideOnly(Side.CLIENT)
public class FileManager {

    private final File dir = new File(Wrapper.getMinecraft().mcDataDir, Simple.CLIENT_NAME);

    private final File modules = new File(dir, "modules.json");

    private final Gson gson = new Gson();

    public FileManager() {
        dir.mkdirs();
    }

    public void saveModules() throws IOException {
        if(!modules.exists())
            modules.createNewFile();

        final JsonObject jsonObject = new JsonObject();

        for(final Module module : ModuleManager.getModules()) {
            final JsonObject moduleJson = new JsonObject();

            moduleJson.addProperty("state", module.getState());
            moduleJson.addProperty("key", module.getKeyBind());
            System.out.println("state:" + module.getState() + " | key:" + module.getKeyBind());

            for(final Value value : module.getValues()) {
                if(value.getObject() instanceof Number)
                    moduleJson.addProperty(value.getName(), (Number) value.getObject());
                else if(value.getObject() instanceof Boolean)
                    moduleJson.addProperty(value.getName(), (Boolean) value.getObject());
                else if(value.getObject() instanceof String)
                    moduleJson.addProperty(value.getName(), (String) value.getObject());
            }

            jsonObject.add(module.getModuleName(), moduleJson);
        }

        final PrintWriter printWriter = new PrintWriter(modules);
        printWriter.println(gson.toJson(jsonObject));
        printWriter.flush();
        printWriter.close();
    }

    public void loadModules() throws IOException {
        if(!modules.exists()) {
            modules.createNewFile();
            saveModules();
            return;
        }

        final BufferedReader bufferedReader = new BufferedReader(new FileReader(modules));

        final JsonElement jsonElement = gson.fromJson(bufferedReader, JsonElement.class);

        if(jsonElement instanceof JsonNull)
            return;

        final JsonObject jsonObject = (JsonObject) jsonElement;

        for(final Module module : ModuleManager.getModules()) {
            if(!jsonObject.has(module.getModuleName()))
                continue;

            final JsonElement moduleElement = jsonObject.get(module.getModuleName());

            if(moduleElement instanceof JsonNull)
                continue;

            final JsonObject moduleJson = (JsonObject) moduleElement;

            if(moduleJson.has("state"))
                module.setState(moduleJson.get("state").getAsBoolean());
            if(moduleJson.has("key"))
             module.setKeyBind(moduleJson.get("key").getAsInt());

            for(final Value value : module.getValues()) {
                if(!moduleJson.has(value.getName()))
                    continue;

                if(value.getObject() instanceof Float)
                    value.setObject(moduleJson.get(value.getName()).getAsFloat());
                else if(value.getObject() instanceof Double)
                    value.setObject(moduleJson.get(value.getName()).getAsDouble());
                else if(value.getObject() instanceof Integer)
                    value.setObject(moduleJson.get(value.getName()).getAsInt());
                else if(value.getObject() instanceof Long)
                    value.setObject(moduleJson.get(value.getName()).getAsLong());
                else if(value.getObject() instanceof Byte)
                    value.setObject(moduleJson.get(value.getName()).getAsByte());
                else if(value.getObject() instanceof Boolean)
                    value.setObject(moduleJson.get(value.getName()).getAsBoolean());
                else if(value.getObject() instanceof String)
                    value.setObject(moduleJson.get(value.getName()).getAsString());
            }
        }
    }
}