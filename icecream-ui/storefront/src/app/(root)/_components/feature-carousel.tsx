import { type FC } from 'react'

import type { CategoryExtended, ProductExtended } from '@/types'
import { EmptyList } from '@/app/_components/empty-list'
import { CategoryCard, ProductCard } from '@/app/_components/item-card'
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel'
import Autoplay from 'embla-carousel-autoplay'
import Link from 'next/link'
import Image from 'next/image'

type Props = {
  data: ProductExtended[]
}

export const FeaturedCarousel: FC<Props> = ({ data }) => (
  <Carousel
    className="w-full mb-12"
    opts={{ loop: true }}
    plugins={[
      Autoplay({
        delay: 10000,
        stopOnInteraction: true,
        stopOnMouseEnter: true,
      })
    ]}
  >
    <CarouselContent>
    {data.length ? (
      <div className="grid grid-cols-3 gap-3">
        {data.map((item: ProductExtended) => (
          <CarouselItem key={item.id}>
            <Link href={`/product/${item.slug}`}>
              <div className="relative mx-auto">
                <Image
                  src="/img/product1.jpg"
                  alt={item.name}
                  height="0"
                  width="0"
                  sizes="100vw"
                  className="w-full h-auto"
                />
                <div className="absolute inset-0 flex items-end justify-center">
                  <h2 className="bg-gray-900 bg-opacity-50 text-2xl font-bold px-2 text-white">
                    {item.name}
                  </h2>
                </div>
              </div>
            </Link>
          </CarouselItem>
        ))}
      </div>
    ) : (
      <EmptyList />
    )}
    </CarouselContent>
    <CarouselPrevious />
    <CarouselNext />
  </Carousel>
)
