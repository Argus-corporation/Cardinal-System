package net.argus.file.cjson;

import java.io.IOException;
import java.util.List;

import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class CJSON {
	
	private List<CJSONObject> objs;
	
	public CJSON(List<CJSONObject> objs) {this.objs = objs;}
	
	public CJSONObject getObject(String name) {return getObject(new CJSONString(name));}
	
	public CJSONObject getObject(CJSONString name) {
		for(CJSONObject obj : objs)
			if(obj.getName().equals(name)) return obj;
		return null;
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for(CJSONItem o : objs.get(0).getItems())
			s += o.toString();
		
		return s;
	}
	
	public static void main(String[] args) throws IOException {
		InitializedSystem.initSystem(new String[] {"-project.name", "Cardinal-System", "-id", "0xdev"}, UserSystem.getDefaultInitializedSystemManager());
		
		//Key key = new Key("*�$ef^�*$�$%kjnkjfezdskpv�$�fdpv�$^dfpv$�^pfe�v^pf$^vp�fqd^v$pqfd^vperpv$qe^rpv$^qerpqv^$eqrpv^*eqorlv^$*ep*v^*$*oeqr^voeqr^voeq^vpq�pr*vq�*ov^pe*ovqerov^qr*ovqrov^qrov�%*�^654654fsdf%���%%sff����%/.�/");
		CJSONFile file = new CJSONFile("manifest", "");
		
		String password = CJSONPareser.parse(file).getObject("package")
				.getValue("manifest").getValue("type").toString();
	
		/*System.out.println(key.decrypt(password).equals("szef�%$�DdgdDE854ED56FDfdgfsdgfgsdGsfdgffdGDFgF855551"));
		System.out.println(key.decrypt(password));*/
		
		System.out.println(password);
		
		//System.out.println(key.crypt("Azef�%$�DdgdDE854ED56FDfdgfsdgfgsdGsfdgffdGDFgF855551"));
		
		/*CJSONBuilder build = new CJSONBuilder();
		
		CJSONObject man = new CJSONObject(new CJSONString("manifest"));
		
		CJSONObject[] sar = new CJSONObject[] {new CJSONString("test"), new CJSONString("bonjour")};
		
		/*List<CJSONItem> item = new ArrayList<CJSONItem>();
		CJSONItem i = new CJSONItem();
		i.setName(new CJSONString("test"));
		i.setValue(new CJSONString("ceci est un test"));
		
		item.add(i);
		
		sar[0].setItems(item);
		sar[1].setItems(item);
		
		CJSONObject ser = new CJSONObject();
		ser.addItem(new CJSONItem(new CJSONString("version"), new CJSONString("1.2")));
		ser.addItem(new CJSONItem(new CJSONString("id"), new CJSONString("true")));
		man.addItemArray(new CJSONItemArray(new CJSONString("array"), sar));
		
		man.addItem(new CJSONItem(new CJSONString("serveur"), ser));
		
		
		build.addObject(man);
		
		
		CJSONFile file = new CJSONFile("manifest", "");
		file.write(build.build());
		*/
		
		UserSystem.exit(0);
	}


}
