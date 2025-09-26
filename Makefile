.PHONY: setup start stop clean build logs

setup:
	@chmod +x scripts/*.sh
	@./scripts/podman-setup.sh

start:
	@./scripts/run-podman.sh

stop:
	@./scripts/stop-podman.sh

clean:
	@podman system prune -af
	@podman volume prune -f

build:
	@podman build -t ecommerce-api .

logs:
	@podman-compose -f podman-compose.yml logs -f

health:
	@curl -f http://localhost:8081/actuator/health | jq .

restart: stop start

# Development commands
dev-start:
	@podman-compose -f podman-compose.yml up -d
	@echo "Services running in background"

dev-logs:
	@podman-compose -f podman-compose.yml logs -f app
