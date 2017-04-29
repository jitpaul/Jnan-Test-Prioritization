package TestCompetition.JavaAgent;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodPrinter extends MethodVisitor implements Opcodes {

	String cName;
	
    public MethodPrinter(final MethodVisitor mv, String cName) {
            super(ASM5, mv);
            this.cName=cName;
    }
		
	@Override
	public void visitLineNumber(int line, Label start) {
		mv.visitLdcInsn(cName + ":" + line);
		mv.visitMethodInsn(INVOKESTATIC, "TestCompetition/JavaAgent/StatementCoverageData", "lineExecuted", "(Ljava/lang/String;)V", false);
		super.visitLineNumber(line, start);

	}
}