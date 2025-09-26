#!/bin/bash

set -e

echo "🐳 Starting ecommerce API with Podman..."

# Ensure Podman machine is running
if ! podman machine list | grep -q "Currently running"; then
    echo "🔄 Starting Podman machine..."
    podman machine start
fi

# Set environment variables
export DOCKER_HOST="unix://$(podman machine inspect --format '{{.ConnectionInfo.PodmanSocket.Path}}')"
export COMPOSE_DOCKER_CLI_BUILD=0

# Build and run services using podman-compose
echo "🏗️  Building and starting services..."
podman-compose -f podman-compose.yml up --build

echo "✅ Services started successfully!"
echo "🌐 API Documentation: http://localhost:8081/swagger-ui.html"
echo "📊 API Health: http://localhost:8081/actuator/health"
