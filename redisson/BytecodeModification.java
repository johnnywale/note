import org.springframework.asm.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BytecodeModification {

    public static byte[] readClassFile(String filePath) throws IOException {
        // Create a Path object from the file path
        Path path = Paths.get(filePath);

        // Read the bytes from the file
        byte[] fileBytes = Files.readAllBytes(path);

        return fileBytes;
    }

    public static byte[] modifyClass(byte[] classBytes) {
        ClassReader reader = new ClassReader(classBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);

        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                System.out.println("name: " + name + " desc:" + desc + " signature:" + signature + " exception:" + exceptions + " res:" + mv);

//                if ((name.equals("a") && desc.equals("()I"))) {
//                    mv.visitCode();
//                    mv.visitLdcInsn(4000);
//
////                    #SIPUSH 4000
////                    mv.visitLdcInsn(4000);
////                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
////                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
//
//                    mv.visitInsn(Opcodes.ARETURN);
//
//

////                    mv.visitLdcInsn(Integer.MAX_VALUE); // Load the long value 1
////                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
////                    mv.visitInsn(Opcodes.ARETURN); // Return the Long object
////                    mv.visitMaxs(2, 1);
//                    mv.visitEnd();
//                    return null; // We've replaced the method, so we don't need to visit it further
//                }
                if ((name.equals("m") && desc.equals("()J"))) {
                    mv.visitCode();
                    mv.visitLdcInsn(-1L);
                    // Return this value
                    mv.visitInsn(Opcodes.LRETURN);
                    mv.visitMaxs(0, 0);

                    //                    mv.visitLdcInsn(-1L); // Load the long value 1
//                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
//                    mv.visitInsn(Opcodes.ARETURN); // Return the Long object
//                    mv.visitMaxs(2, 1);
                    mv.visitEnd();
                    return null; // We've replaced the method, so we don't need to visit it further
                }

                if ((name.equals("m") && desc.equals("()Ljava/lang/Long;"))) {
                    mv.visitCode();
                    mv.visitLdcInsn(-1L); // Load the long value 1
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                    mv.visitInsn(Opcodes.ARETURN); // Return the Long object
                    mv.visitMaxs(2, 1);
                    mv.visitEnd();
                    return null; // We've replaced the method, so we don't need to visit it further
                }
                return mv;
            }
        };

        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        byte[] originalClassBytes = readClassFile("C:\\git\\github\\spring-security-oauth\\spring-security-oauth2\\src\\main\\java\\B.class");
        byte[] modifiedClassBytes = BytecodeModification.modifyClass(originalClassBytes);
        Path path = Paths.get("C:\\git\\github\\spring-security-oauth\\spring-security-oauth2\\src\\main\\java\\C.class");
        Files.write(path, modifiedClassBytes);

    }
}

