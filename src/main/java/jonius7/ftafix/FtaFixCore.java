package jonius7.ftafix;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class FtaFixCore implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{APITransformer.class.getName()};
    }

    @Override public String getAccessTransformerClass() { return null; }
    @Override public void injectData(java.util.Map<String, Object> data) { }
    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }

    public static class APITransformer implements IClassTransformer {

        private static final String TARGET_PACKAGE_PREFIX = "forestry/api";

        @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {
            // Early logging: safe
            //System.out.println("[FTA Fix] Checking class: " + transformedName);

            if (!transformedName.startsWith(TARGET_PACKAGE_PREFIX)) return basicClass;

            System.out.println("[FTA Fix] Neutralizing class: " + transformedName);

            try {
                ClassReader cr = new ClassReader(basicClass);
                ClassWriter cw = new ClassWriter(0);

                ClassVisitor cv = new ClassVisitor(Opcodes.ASM4, cw) {
                    @Override
                    public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] exceptions) {
                        System.out.println("[FTA Fix] Stubbing method: " + transformedName + "." + name + desc);
                        return new MethodVisitor(Opcodes.ASM4) {
                            @Override
                            public void visitCode() {
                                visitInsn(Opcodes.RETURN);
                                visitMaxs(0, 0);
                                visitEnd();
                            }
                        };
                    }

                    @Override
                    public FieldVisitor visitField(int access, String name, String desc, String sig, Object value) {
                        System.out.println("[FTA Fix] Removing field: " + transformedName + "." + name);
                        return null;
                    }
                };

                cr.accept(cv, 0);
                return cw.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
                return basicClass;
            }
        }
    }
}
