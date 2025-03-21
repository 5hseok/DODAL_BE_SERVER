FROM openjdk:21-jdk-slim AS builder
WORKDIR /buildMyApp

# Gradle Wrapper 복사
COPY gradlew settings.gradle ./
COPY gradle gradle
RUN chmod +x gradlew

# 의존성 파일 복사 및 다운로드
COPY build.gradle .


# 의존성 다운로드
RUN ./gradlew dependencies

# 소스코드 복사 및 빌드
COPY src ./src
# PR에서 테스트를 이미 시행하여 검증했기 때문에 테스트 제외
# profile prod로 빌드
RUN ./gradlew clean build -x test

# 실행 스테이지
FROM openjdk:21-jdk-slim
WORKDIR /dodalApp
COPY --from=builder /buildMyApp/build/libs/*.jar dodalAppExcute.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=prod", "-jar", "dodalAppExcute.jar"]