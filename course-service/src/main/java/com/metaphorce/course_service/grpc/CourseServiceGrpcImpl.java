package com.metaphorce.course_service.grpc;

import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;

@GrpcService
public class CourseServiceGrpcImpl extends CourseServiceGrpc.CourseServiceImplBase {

    @Override
    public void getCourse(CourseRequest request, StreamObserver<CourseResponse> responseObserver) {
        String courseId = request.getId();

        // Simular obtención del curso (normalmente consultarías MongoDB u otro servicio)
        CourseResponse response = CourseResponse.newBuilder()
                .setId(courseId)
                .setName("Curso de ejemplo con gRPC")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
