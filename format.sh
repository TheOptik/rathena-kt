#!/usr/bin/env bash
set -euo pipefail

KTLINT_VERSION="1.5.0"

echo "🔧 Running ktlint formatter (v${KTLINT_VERSION})..."
docker run --rm \
  -v "$(pwd):/work" \
  -w /work \
  "pinterest/ktlint:${KTLINT_VERSION}" \
  --format "src/**/*.kt"

echo "✅ Done! All Kotlin files formatted."
