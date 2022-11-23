package im.zhaojun.zfile.config.inspector;

import java.util.Map;

import com.amazonaws.xray.entities.Subsegment;
import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class XRayInspector extends AbstractXRayInterceptor {

	//	static {
	//		AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder
	//				.standard()
	//				.withSegmentListener(new SegmentListener());
	//		AWSXRay.setGlobalRecorder(builder.build());
	//	}

	@Override
	protected Map<String, Map<String, Object>> generateMetadata(ProceedingJoinPoint proceedingJoinPoint,
			Subsegment subsegment) {
		return super.generateMetadata(proceedingJoinPoint, subsegment);
	}

	@Override
	@Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && bean(*Controller)")
	public void xrayEnabledClasses() {
	}

}
