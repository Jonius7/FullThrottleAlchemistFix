package jonius7.ftafix;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

import java.util.Map;

// ---------------------------
// Coremod entry point
// ---------------------------
@IFMLLoadingPlugin.Name("FTA Fix")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class FtaFixCore implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{APITransformer.class.getName()};
    }

    @Override
    public String getAccessTransformerClass() { return null; }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getModContainerClass() { return null; }

    @Override
    public String getSetupClass() { return null; }

    // ---------------------------
    // ASM Transformer
    // ---------------------------
    public static class APITransformer implements IClassTransformer {

        @Override
        public byte[] transform(String name, String transformedName, byte[] basicClass) {
            if (basicClass == null) return null;

            // Only touch forestry/api classes
            if (name == null || !name.startsWith("forestry/api/")) return basicClass;

            // If Forestry proper has already loaded, skip patching
            // (Forestry will define the real classes itself later)
            if (name.startsWith("forestry/api/core") || name.startsWith("forestry/api/plugins")) {
                return basicClass; // Let Forestry load its own core API parts
            }

            FMLLog.info("[FTA Fix] Neutralizing embedded Forestry API class: %s", name);

            try {
                ClassWriter cw = new ClassWriter(0);

                // Create an empty stub class
                cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC, name, null, "java/lang/Object", null);

                // Add default constructor
                MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
                mv.visitCode();
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                mv.visitInsn(Opcodes.RETURN);
                mv.visitMaxs(1, 1);
                mv.visitEnd();

                cw.visitEnd();
                return cw.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
                return basicClass;
            }
        }
    }
}
